
const e=performance.now();
if(this.pollGamepadsLastStartExecutionTimestamp>0&&
this.inputPollingIntervalStats.addValue(e-this.pollGamepadsLastStartExecutionTimestamp),
this.pollGamepadsLastStartExecutionTimestamp=e,!this.isStarted||this.isPaused)return void this.stopGamepadPolling();
this.inputConfiguration.enableSensorInput&&
this.sensorManager&&
(this.currentSensorReading=this.sensorManager.getSensorReading(),
this.currentSensorReading.accelerationTimestamp==this.lastSensorReading.accelerationTimestamp&&
this.currentSensorReading.angularVelocityTimestamp==this.lastSensorReading.angularVelocityTimestamp&&
this.currentSensorReading.magneticFieldTimestamp==this.lastSensorReading.magneticFieldTimestamp&&
this.currentSensorReading.orientationTimestamp==this.lastSensorReading.orientationTimestamp||
(this.inputSink.onSensorInput(this.currentSensorReading),this.lastSensorReading=a()({},this.currentSensorReading)));
const t=navigator.getGamepads();
for(let a of t){
if(!a||!this.deviceInformation.capabilities.allowNonstandardGamepad&&!(0,h.RP)(a))continue;
if(!a.connected){
this.removeGamepad(a.index,a.id);continue}
this.inputConfiguration.gamepadTransformer&&
(a=this.inputConfiguration.gamepadTransformer(a));
let e=this.gamepadMappings.find((e=>{var t;return e.GamepadIndex===(null===(t=a)||void 0===t?void 0:t.index)}));
if(!e){
if(e=this.addGamepad(a),!e)continue;
this.logger.info(`Received gamepad while polling that was not previously added with index: ${a.index}, id: ${a.id}.`)}
const t=this.gamepadTimestamps.get(a.index),
c=this.gamepadIsIdle.get(a.index);
if(void 0===t||a.timestamp>t){
var i,n,r,s,o,d,l,u,p,g,m,v,S,y,f,b,E,C,T,I,M,k,P,A,L,D,x,V,F,U,$,B,z,N,O,G;
this.gamepadTimestamps.set(a.index,a.timestamp),this.gamepadIsIdle.set(a.index,!1);
const h=e.PhysicalPhysicality;
e.GamepadIndex=a.index,e.A=null!==(i=null===(n=a.buttons[0])||void 0===n?void 0:n.value)&&
void 0!==i?i:0,e.B=null!==(r=null===(s=a.buttons[1])||void 0===s?void 0:s.value)&&
void 0!==r?r:0,e.X=null!==(o=null===(d=a.buttons[2])||void 0===d?void 0:d.value)&&v
oid 0!==o?o:0,e.Y=null!==(l=null===(u=a.buttons[3])||void 0===u?void 0:u.value)&&
void 0!==l?l:0,e.LeftShoulder=null!==(p=null===(g=a.buttons[4])||void 0===g?void 0:g.value)&&
void 0!==p?p:0,e.RightShoulder=null!==(m=null===(v=a.buttons[5])||void 0===v?void 0:v.value)&&
void 0!==m?m:0,e.LeftTrigger=null!==(S=null===(y=a.buttons[6])||void 0===y?void 0:y.value)&&
void 0!==S?S:0,e.RightTrigger=null!==(f=null===(b=a.buttons[7])||void 0===b?void 0:b.value)&&
void 0!==f?f:0,e.View=null!==(E=null===(C=a.buttons[8])||void 0===C?void 0:C.value)&&
void 0!==E?E:0,e.Menu=null!==(T=null===(I=a.buttons[9])||void 0===I?void 0:I.value)&&
void 0!==T?T:0,e.LeftThumb=null!==(M=null===(k=a.buttons[10])||void 0===k?void 0:k.value)&&
void 0!==M?M:0,e.RightThumb=null!==(P=null===(A=a.buttons[11])||void 0===A?void 0:A.value)&&
void 0!==P?P:0,e.DPadUp=null!==(L=null===(D=a.buttons[12])||void 0===D?void 0:D.value)&&
void 0!==L?L:0,e.DPadDown=null!==(x=null===(V=a.buttons[13])||void 0===V?void 0:V.value)&&
void 0!==x?x:0,e.DPadLeft=null!==(F=null===(U=a.buttons[14])||void 0===U?void 0:U.value)&&
void 0!==F?F:0,e.DPadRight=null!==($=null===(B=a.buttons[15])||void 0===B?void 0:B.value)&&
void 0!==$?$:0,e.Nexus=null!==(z=null===(N=a.buttons[16])||void 0===N?void 0:N.value)&&
void 0!==z?z:0,e.LeftThumbXAxis=null!==(O=a.axes[0])&&
void 0!==O?O:0,e.LeftThumbYAxis=null!=a.axes[1]?-a.axes[1]:0,e.RightThumbXAxis=null!==(G=a.axes[2])&&
void 0!==G?G:0,e.RightThumbYAxis=null!=a.axes[3]?-a.axes[3]:0,e.PhysicalPhysicality=(0,R.Z)(e),e.Dirty=!0,e.Virtual=!1,
t&&h!=e.PhysicalPhysicality&&
this.inputFeedbackManager.onGamepadConnected(a.index)}
else c||(e.PhysicalPhysicality=(0,R.Z)(e,.1),e.PhysicalPhysicality==R.J.None&&this.gamepadIsIdle.set(a.index,!0));
if(this.inputConfiguration.useNexusPressWorkaround){
var _,W;
const t=Date.now(),i=null!==(_=null===(W=a.buttons[16])||void 0===W?void 0:W.value)&&
void 0!==_?_:0;t<this.sendNexusPressedUntil?e.Nexus=w.Pressed:this.prevNexusState.value==w.Pressed&&i==w.Released?(this.sendNexusPressedUntil=t+(a.timestamp-this.prevNexusState.gamepadTimestamp),e.Nexus=w.Pressed,this.prevNexusState={value:w.Released,gamepadTimestamp:a.timestamp}):(this.prevNexusState={value:i,gamepadTimestamp:a.timestamp},e.Nexus=i)}}
this.inputSink.onGamepadInput(e,this.gamepadMappings),this.removeMissingGamepads(t);
const K=performance.now()-e;this.inputPollingDurationStats.addValue(K);
for(const a of this.gamepadMappings)
a.Dirty=!1;
var Y;
this.inputPollingType===c.mb.IntervalTimer?this.pollGamepadssetTimeoutTimerID=setTimeout(this.pollGamepads,Math.max(0,this.pollGamepadsIntervalMs-K)):null===(Y=this.intervalWorker)||void 0===Y||Y.scheduleTimer(Math.max(0,this.pollGamepadsIntervalMs-K))