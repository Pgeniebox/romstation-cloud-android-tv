// ==UserScript==
// @name         agp ctx (agl-safe)
// @namespace    http://tampermonkey.net/
// @description  Improve psnow Cloud Gaming (xCloud) experience

// @version      1.0.0
// @match        https://psnow.playstation.com/*
// @grant        none
// @run-at       document-start
// ==/UserScript==




(function () {
  'use strict';

  function buildWorkerScript() {
    return `
(() => {
  const COMMANDS = {
    startGame: { name: "startGame", target: "AGL" },
    requestGame: { name: "requestGame", target: "AGL" },
    requestClientId: { name: "requestClientId", target: "AGL" },
    stop: { name: "stop", target: "AGL" },
    testConnection: { name: "testConnection", target: "AGL" },
    isStreaming: { name: "isStreaming", target: "AGL" },
    isQueued: { name: "isQueued", target: "AGL" },
    isShuttingDown: { name: "isShuttingDown", target: "AGL" },
    getWindowPosition: { name: "getWindowPositionResponse", target: "AGL" },
    windowControl: { name: "windowControl", target: "AGL" }
  };

  function emit(type, payload) {
    self.postMessage({ type: type, payload: payload });
  }

  function ack(name, result, target) {
    emit("gaikai::event", {
      type: "event",
      id: 0,
      name: name,
      result: JSON.stringify(result || { status: "ok" }),
      source: "AGL",
      target: target || "AGL"
    });
  }

  function handleCommand(payload) {
    const command = payload && payload.command;
    const params = payload && payload.params ? payload.params : {};
    const target = payload && payload.target ? payload.target : "AGL";

    if (command === "getVersion") {
      emit("gaikai::event", {
        type: "versionInfo",
        name: "12.7.0",
        source: "AGL",
        target: target
      });
      return;
    }

    if (command === "getPrivacySetting") {
      emit("gaikai::event", {
        type: "privacySetting",
        name: "advanced",
        code: "advanced",
        result: JSON.stringify({}),
        source: "AGL",
        target: target
      });
      return;
    }

    if (command === "isStreaming") {
      emit("gaikai::event", {
        type: "isStreaming",
        name: "false",
        source: "AGL",
        target: target
      });
      return;
    }

    if (command === "isQueued") {
      emit("gaikai::event", {
        type: "isQueued",
        name: "false",
        source: "AGL",
        target: target
      });
      return;
    }

    if (command === "isShuttingDown") {
      emit("gaikai::event", {
        type: "isShuttingDown",
        name: "false",
        source: "AGL",
        target: target
      });
      return;
    }

  if (command === "sendMessage") {
          const raw = params && params.message;

            if (raw === "isQASReadyToListen") {
              emit("gaikai::event", {
                command: "sendMessage",
                params: { message: "notifyQASReady" },
                source: "QAS",
                target: "AGL"
              });
              return;
            }

            // Check if this is a windowFocused.QAS message and translate it to a window event
            if (typeof raw === "string") {
              try {
                const parsed = JSON.parse(raw);
                if (parsed && parsed.message && parsed.message.indexOf("windowFocused.") === 0) {
                  const isFocused = parsed.isFocused === true;
                  emit("gaikai::event", {
                    type: "windowEvent",
                    name: isFocused ? "focus" : "blur",
                    source: "AGL",
                    target: target || payload.source || "AGL"
                  });
                  return;
                }
                if (parsed && parsed.message === "isQASReadyToListen") {
                  emit("gaikai::event", {
                    command: "sendMessage",
                    params: { message: "notifyQASReady" },
                    source: "QAS",
                    target: "AGL"
                  });
                  return;
                }
              } catch (e) {
                // Not JSON or parse error, fall through to normal message handling
              }
            }

  emit("gaikai::event", {
    command: "sendMessage",
    params: { message: params.message },
    source: payload.source,
    target: target
  });
  return;
}

    if (command === "sendConnectedControllerEvent") {
      const controllerId = (params && params.controllerId) || "gamepad";
      emit("gaikai::event", {
        type: "event",
        id: 0,
        name: "connectedControllerEvent",
        result: JSON.stringify({
          status: "ok",
          controllerId: 0,
          type: "gamepad",
          connected: true
        }),
        source: "AGL",
        target: target || "AGL"
      });
      return;
    }

    if (COMMANDS[command]) {
      const result = { status: "ok" };
      const name = COMMANDS[command].name;
      ack(name, result, target);
      return;
    }

    if (command === "windowControl") {
      ack("windowControl", { status: "ok" }, target);
      return;
    }

    if (command === "setUrl") {
      ack("setUrl", { status: "ok" }, target);
      return;
    }

    if (command === "setUrlDefaultBrowser") {
      ack("setUrlDefaultBrowser", { status: "ok" }, target);
      return;
    }

    if (command === "showDevTools") {
      ack("showDevTools", { status: "ok" }, target);
      return;
    }

    emit("gaikai::event", {
      type: "event",
      id: 0,
      name: "ack",
      result: JSON.stringify({ status: "ok" }),
      source: "AGL",
      target: target
    });
  }

  self.addEventListener("message", function (event) {
    const msg = event && event.data ? event.data : undefined;
    if (!msg) { return; }

    if (msg.type === "__gaikai__init__") {
      emit("gaikai::ready", { ok: true });
      return;
    }

    if (msg.type === "__gaikai__send__") {
      const payload = msg.payload;
      if (payload && typeof payload === "string") {
        try {
          handleCommand(JSON.parse(payload));
        } catch (e) {
          emit("gaikai::error", e);
        }
      } else {
        handleCommand(payload);
      }
    }
  });

  emit("gaikai::ready", { ok: true });
})();
    `;
  }

  function GKPWorkerBridge() {
    this._onError = undefined;
    this._onEvent = undefined;
    this._ready = true;
    this.focus = false;
    this.isConnected = true;
    this.identity = 'QAS';
    this.localPath = typeof __dirname !== 'undefined' ? __dirname : undefined;
    this._eventListeners = {};
    this._eventOnceListeners = {};
    this._eventQueue = [];
    this._keyStates = {};
    this._worker = undefined;

    this._initWorker();
  }

  GKPWorkerBridge.prototype.emit = function (eventName) {
    var args = Array.prototype.slice.call(arguments, 1);
    if (eventName.indexOf('window-') === 0) {
      console.log('[GKP Bridge] Emitting event:', eventName, args);
    }
    var listeners = this._eventListeners[eventName] || [];
    for (var i = 0; i < listeners.length; i++) {
      listeners[i].apply(null, args);
    }

    var onceListeners = this._eventOnceListeners[eventName] || [];
    for ( i = 0; i < onceListeners.length; i++) {
      onceListeners[i].apply(null, args);
    }
    delete this._eventOnceListeners[eventName];
  };

  GKPWorkerBridge.prototype.on = function (eventName, listener, uid) {
    listener.uid = uid;
    this.emit('newListener', eventName, listener);
    if (!(eventName in this._eventListeners)) {
      this._eventListeners[eventName] = [];
    }
    this._eventListeners[eventName].push(listener);
  };

  GKPWorkerBridge.prototype.addListener = GKPWorkerBridge.prototype.on;

  GKPWorkerBridge.prototype.once = function (eventName, listener) {
    if (!(eventName in this._eventOnceListeners)) {
      this._eventOnceListeners[eventName] = [];
    }
    this._eventOnceListeners[eventName].push(listener);
  };

  GKPWorkerBridge.prototype.removeListener = function (eventName, listener, uid) {
    if (eventName in this._eventListeners) {
      this._eventListeners[eventName] = this._eventListeners[eventName].filter(function (i) {
        return i !== listener && (uid === undefined || i.uid !== uid);
      });
    }
    if (eventName in this._eventOnceListeners) {
      this._eventOnceListeners[eventName] = this._eventOnceListeners[eventName].filter(function (i) {
        return i !== listener && (uid === undefined || i.uid !== uid);
      });
    }
    this.emit('removeListener', eventName, listener);
  };

  GKPWorkerBridge.prototype._initWorker = function () {
    if (typeof Worker === 'undefined') {
      console.error('Worker not supported in this environment');
      return;
    }

    var script = buildWorkerScript();
    var blob = new Blob([script], { type: 'application/javascript' });
    var workerUrl = URL.createObjectURL(blob);
    this._worker = new Worker(workerUrl);

    this._worker.addEventListener('message', function (event) {
      var msg = event && event.data ? event.data : undefined;
      if (!msg) {
        return;
      }

      if (msg.type === 'gaikai::ready') {
        console.log('[GKP Bridge] Worker ready');
        this._ready = true;
        this.isConnected = true;
        this.emit('connected');
        while (this._eventQueue.length) {
          this._ipcHandler(this._eventQueue.shift());
        }
        return;
      }

      if (msg.type === 'gaikai::error') {
        console.error('[GKP Bridge] Worker error:', msg.payload);
        if (typeof this._onError === 'function') {
          this._onError(msg.payload);
        }
        this.emit('error', msg.payload);
        return;
      }

      if (msg.type === 'gaikai::event') {
        console.log('[GKP Bridge] Event from worker:', msg.payload.name || msg.payload.type, msg.payload);
        this._ipcHandler(msg.payload);
      }
    }.bind(this));

    this._worker.addEventListener('error', function (err) {
      console.error('[GKP Bridge] Worker error event:', err);
      if (typeof this._onError === 'function') {
        this._onError(err);
      }
      this.emit('error', err);
    }.bind(this));

    console.log('[GKP Bridge] Initializing worker...');
    this._worker.postMessage({ type: '__gaikai__init__' });
  };

  GKPWorkerBridge.prototype._ipcHandler = function (data) {
    if (!data || typeof data !== 'object') {
      return;
    }

    var isError = data.type === 'error';
    var isEvent = ['event', 'gamepadEvent', 'psHomeButton', 'versionInfo', 'gamepadMgrEvent', 'localeInfo', 'privacySetting', 'isStreaming', 'isQueued', 'isShuttingDown'].indexOf(data.type) !== -1;
    var isWindowEvent = !isEvent && data.type === 'windowEvent';
    var isMessage = data.command === 'sendMessage';

    // If not ready yet and it's not a critical event, queue it
    if (!this._ready && !isWindowEvent && !isEvent) {
      if (!this._handleGamepadData(data)) {
        if (data.type === 'localeInfo') {
          this.onLocaleInfo(data.uiLanguages);
        } else {
          console.log('[GKP Bridge] Queuing message while not ready:', data.command || data.type);
          this._eventQueue.push(data);
        }
      }
      return;
    }

    if (isError) {
      if (typeof this._onError === 'function') {
        this._onError(data);
      }
      this.emit('error', data);
      return;
    }

    if (isWindowEvent) {
      if (data.name === 'focus') {
        //this.onFocus();
      } else if (data.name === 'blur') {
        //this.onBlur();
      } else {
        this.emit('window-' + data.name, data);
      }
      return;
    }

    if (isEvent) {
      if (!this._handleGamepadData(data)) {
        if (data.type === 'localeInfo') {
          this.onLocaleInfo(data.uiLanguages);
        } else if (typeof this._onEvent === 'function') {
          this._onEvent(data);
        }
        this.emit('event', data);
      }
      return;
    }

    if (isMessage) {
      if (typeof data.params !== 'object' || typeof data.params.message !== 'string') {
        console.error('malformed sendMessage packet');
      } else if ((typeof data.target === 'undefined') || (data.target === '') || (data.target === this.identity)) {
        console.log('[GKP Bridge] Emitting message:', data.params.message);
        this.emit('message', data.params.message);
      }
    }
  };

  GKPWorkerBridge.prototype._handleGamepadData = function (obj) {
    if (obj.type === 'gamepadEvent' || obj.type === 'psHomeButton') {
      var windowState = window.gaikai && window.gaikai.windowState ? window.gaikai.windowState : {};
      if (windowState.allowUnfocusedGamepadInput !== false && this.focus) {
        return false;
      }
      return true;
    }
    return false;
  };

GKPWorkerBridge.prototype.onFocus = function () {
    return;
  this.focus = true;
  this.emit('window-focus');
};

GKPWorkerBridge.prototype.onBlur = function () {
    return;
  this.focus = false;
  this.emit('window-blur');
};

  GKPWorkerBridge.prototype.onLocaleInfo = function (uiLanguages) {
    if (typeof uiLanguages !== 'undefined') {
      window.navigator.userLanguage = uiLanguages[0];
      if (typeof window.gaikai.localeInfo === 'undefined') {
        window.gaikai.localeInfo = {};
      }
      window.gaikai.localeInfo.uiLanguages = uiLanguages;
    }
  };

  GKPWorkerBridge.prototype._genPromise = function () {
    return new Promise(function (resolve) { resolve(); });
  };

  GKPWorkerBridge.prototype.send = function (obj, target) {
    if (!this._worker) {
      console.warn('Bridge send called but worker not initialized:', obj);
      return;
    }

    var payload = Object.assign({}, obj, { source: this.identity });
    if (typeof target !== 'undefined' && typeof target !== 'string') {
      throw 'TARGET parameter must be a string, or undefined';
    }
    if (typeof target !== 'undefined') {
      payload.target = target;
    }

    this._worker.postMessage({ type: '__gaikai__send__', payload: payload });
    console.log('[GKP Bridge] send:', payload.command, payload);
  };

  GKPWorkerBridge.prototype.ready = function (ready) {
    if (typeof ready === 'undefined') {
      ready = true;
    }
    this._ready = ready;
    if (this._ready) {
      while (this._eventQueue.length) {
        this._ipcHandler(this._eventQueue.shift());
      }
    }
  };

  GKPWorkerBridge.prototype.getDuid = function () {
    if (typeof window !== 'undefined' && window.__FAKE_DUID__) {
      return window.__FAKE_DUID__;
    }
    return 'fake-duid-' + Math.random().toString(36).slice(2, 10);
  };

  GKPWorkerBridge.prototype.getLocale = function () {
    return 'en-US' ||(navigator && (navigator.language || navigator.userLanguage)) || 'en-US';
  };

  GKPWorkerBridge.prototype.getVersion = function () {
    this.send({ command: 'getVersion', params: {} }, 'AGL');
    return this._genPromise();
  };

 GKPWorkerBridge.prototype.updater  =function(){return!0};

     GKPWorkerBridge.prototype._resetGamepads = function () {

  };
  GKPWorkerBridge.prototype.getPrivacySetting = function () {
    this.send({ command: 'getPrivacySetting', params: {} }, 'AGL');
    return this._genPromise();
  };

  GKPWorkerBridge.prototype.isStreaming = function () {
    this.send({ command: 'isStreaming', params: {} }, 'AGL');
    return this._genPromise();
  };

  GKPWorkerBridge.prototype.isQueued = function () {
    this.send({ command: 'isQueued', params: {} }, 'AGL');
    return this._genPromise();
  };

  GKPWorkerBridge.prototype.isShuttingDown = function () {
    this.send({ command: 'isShuttingDown', params: {} }, 'AGL');
    return this._genPromise();
  };

  GKPWorkerBridge.prototype.sendConnectedControllerEvent = function (controllerId) {
    this.send({
      command: 'sendConnectedControllerEvent',
      params: {
        controllerId: controllerId || 'gamepad'
      }
    }, 'AGL');
    return this._genPromise();
  };

  GKPWorkerBridge.prototype.setCallbacks = function (onEvent, onError) {
    this._onEvent = onEvent;
    this._onError = onError;
    return this._genPromise();
  };

 GKPWorkerBridge.prototype._clearGamepadEventsFromQueue  = function () {

  };
  GKPWorkerBridge.prototype.sendMessage = function (message, target) {
    if (typeof message !== 'string') {
      console.error('sendMessage called with argument type of \'' + typeof message + '\', attempting to auto-stringify. source: ' + this.identity);
      message = (typeof message === 'object') ? JSON.stringify(message) : String(message);
    }
    this.send({ command: 'sendMessage', params: { message: message } }, typeof target !== 'undefined' ? target : 'AGL');
  };

  GKPWorkerBridge.prototype.windowControl = function (command, target) {
    this.send({ command: 'windowControl', params: { command: command } }, target || 'AGL');
    return true;
  };

  GKPWorkerBridge.prototype.setUrl = function (url, target) {
    this.send({ command: 'setUrl', params: { url: url } }, target || 'AGL');
  };

  GKPWorkerBridge.prototype.setUrlDefaultBrowser = function (url) {
    this.send({ command: 'setUrlDefaultBrowser', params: { url: url } }, 'AGL');
  };

  GKPWorkerBridge.prototype.showDevTools = function (show) {
    this.send({ command: 'showDevTools', params: { show: show } }, this.identity);
  };

  GKPWorkerBridge.prototype.qasTooltip = function (params) {
    this.send({ command: 'qasTooltip', params: params }, 'AGL');
  };

  GKPWorkerBridge.prototype.trayNotification = function (title, message, iconType) {
    this.send({ command: 'trayNotification', params: { title: title, message: message, iconType: iconType } }, 'AGL');
  };

  GKPWorkerBridge.prototype.qasTrayIcon = function (nameOfIcon) {
    this.send({ command: 'qasTrayIcon', params: nameOfIcon }, 'AGL');
  };

  GKPWorkerBridge.prototype.showSplashScreen = function (show) {
    this.send({ command: 'qasSplashScreen', params: { show: show } }, 'AGL');
  };

  GKPWorkerBridge.prototype.notificationWindowSetVisible = function (visible) {
    this.send({ command: 'notificationWindow', params: { command: visible ? 'show' : 'hide' } }, 'AGL');
  };

  GKPWorkerBridge.prototype.notificationWindowSetSize = function (width, height) {
    this.send({ command: 'notificationWindow', params: { command: 'setSize', width: parseInt(width, 10), height: parseInt(height, 10) } }, 'AGL');
  };

  GKPWorkerBridge.prototype.notificationWindowSetUrl = function (url) {
    this.send({ command: 'notificationWindow', params: { command: 'setContent', content: url } }, 'AGL');
  };

  GKPWorkerBridge.prototype.notificationWindowSetFadeDuration = function (fadeDuration) {
    this.send({ command: 'notificationWindow', params: { command: 'setAnimDurations', fadeDuration: parseInt(fadeDuration, 10), scrollDuration: parseInt(fadeDuration, 10) } }, 'AGL');
  };

  GKPWorkerBridge.prototype.qasTrayMenu = function (params) {
    this.send({ command: 'qasTrayMenu', params: params }, 'AGL');
  };

  GKPWorkerBridge.prototype.localRumbleEvent = function (playerID, largeMotor, smallMotor, durationMS) {
    this.send({ command: 'localRumbleEvent', params: { playerID: playerID, largeMotor: largeMotor, smallMotor: smallMotor, durationMS: durationMS } }, 'AGL');
  };

  GKPWorkerBridge.prototype.initializeLib = function () {
    // NOOP
  };

  GKPWorkerBridge.prototype.shutdownLib = function () {
    // NOOP
  };

  GKPWorkerBridge.prototype.setTopmostWindow = function (topmostWindowName) {
    this.send({ command: 'setTopmostWindow', params: { topmost: topmostWindowName } }, 'AGL');
  };

  var gaikaiPlayer = new GKPWorkerBridge();

  window.gaikaiPlayer = gaikaiPlayer;
  window.gaikai = {
    ipc: gaikaiPlayer,
    localPath: gaikaiPlayer.localPath,
    setConnected: function (connected) {
      gaikaiPlayer.isConnected = connected;
    },
    ready: function (readyState) {
      gaikaiPlayer.ready(readyState);
    }
  };

 //window.addEventListener('focus', gaikaiPlayer.onFocus.bind(gaikaiPlayer));
//window.addEventListener('blur', gaikaiPlayer.onBlur.bind(gaikaiPlayer));

  if (window && typeof window !== 'undefined') {
    window.gaikai.localPath = gaikaiPlayer.localPath;
  }
})();
// ==UserScript==
// @name         PSN Apollo Service Container Shim
// @namespace    http://tampermonkey.net/
// @version      1.0.0
// @match        https://psnow.playstation.com/*
// @grant        none
// @run-at       document-start
// ==/UserScript==

// LGE webOS
(function () {
    const s = document.createElement("script");

    s.textContent = `
        const wait = setInterval(() => {
        if(window.sce!==undefined){
           window.sce.hasNeoMode=function h(){ return true;            }
   window.sce.platform="PC";
//navigator.userAgent='5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) playstation-now/0.0.0 Chrome/83.0.4103.104 Electron/9.0.4 Safari/537.36 gkApollo'
           clearInterval(wait);

     }
     return;
      const requirejs = window.require || window.requirejs;

            if (!requirejs) {
                return;
            }

           const InputModule = requirejs("apollo/components/detail-overview/pointer/component");

            if (!InputModule || !InputModule.default) {
                return;
            }
             const proto = InputModule.default.prototype;

            if (!proto &&undefined==proto.init) {
                return;
            }
  const original = proto.init;

            proto.init = function (...args) {
                            original.apply(this, args);

          window.cxinput=this;


            };


clearInterval(wait);

            console.log("handleKey hook installed");

        }, 0);
    `;

    document.documentElement.appendChild(s);
    s.remove();
})();


localStorage.setItem("chosenPlatform","PC");

/*(() => {
    const originalAssign = Object.assign;

    Object.assign = function (...args) {
        if(args[1].language!=undefined){
           args[1].cloudEndpoint='';
        console.log(this,args[1]);}

        return originalAssign.apply(this, args);
    };
})();*/

/*(() => {

    const originalFetch = window.fetch;

    window.fetch = async (...args) => {
        const response = await originalFetch(...args);

        console.log("FETCH URL:", args[0]);

        if (args[1]) {
            console.log("FETCH OPTIONS:", args[1]);
        }

        const clone = response.clone();

        try {
            const text = await clone.text();

            console.log("FETCH RESPONSE:", text);
        } catch (e) {}

        return response;
    };

    const originalOpen = XMLHttpRequest.prototype.open;
    const originalSend = XMLHttpRequest.prototype.send;

    XMLHttpRequest.prototype.open = function (method, url) {
        this._url = url;
        this._method = method;

        return originalOpen.apply(this, arguments);
    };

    XMLHttpRequest.prototype.send = function (body) {
        console.log("XHR:", this._method, this._url);

        if (body) {
            console.log("XHR BODY:", body);
        }

        this.addEventListener("load", () => {
            console.log("XHR STATUS:", this.status);

            try {
                console.log("XHR RESPONSE:", this.responseText);
            } catch (e) {}
        });

        return originalSend.apply(this, arguments);
    };
})();*/
/*
document.addEventListener(
    "keydown",
    function (e) {
     /*   if (e.key === "Escape") {
            e.preventDefault();
            e.stopPropagation();
            e.stopImmediatePropagation();

           document.dispatchEvent(
    new KeyboardEvent("keydown", {
        key: "B",
        code: "BrowserBack",
        keyCode: 166,
        which: 166,
        bubbles: true
    })
);

            return false;
        }
        console.log(e);
    },
    true
);*/


/*

(function () {
    const responseText = Object.getOwnPropertyDescriptor(
        XMLHttpRequest.prototype,
        "responseText"
    );

    Object.defineProperty(XMLHttpRequest.prototype, "responseText", {
        get() {
            let value = responseText.get.call(this);

            if (this.readyState === 4 && typeof value=='string'&&value.includes('account_type')) {

                try {
                    let ko= JSON.parse(value);
                    let nd = ko.data;
if(nd.account_type==1){
nd.account_type=0;nd.country="US";nd.currencies[0].code= "USD"
            nd.currencies[0].symbol= "$";
    ko.data=nd;
        ko=JSON.stringify(ko);

    value=ko;
}
                    console.log(
                        "[XHR]",
                       nd
                    );
                } catch {}
            }

            return value;
        }
    });
})();*/
/*const originalSend = XMLHttpRequest.prototype.send;

XMLHttpRequest.prototype.send = function () {
    const xhr = this;

    const original = xhr.onreadystatechange;

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {


            try {
                if(typeof xhr.responseText=='string'&&xhr.responseText.includes('account_type')){
            let ko= JSON.parse(xhr.responseText);
                    let nd = ko.data;
if(nd.account_type==1){
nd.account_type=0;nd.country="US";nd.currencies[0].code= "USD"
            nd.currencies[0].symbol= "$";
    ko.data=nd;
        ko=JSON.stringify(ko);
xhr.responseText=ko;
}}}catch(e){}

        }

        if (original) {
            return original.apply(this, arguments);
        }
    };

    return originalSend.apply(this, arguments);
};*/