package com.world.cloudxsolution.romstation;

import java.util.List;

public class Models {

    public static class BaseResponse {
        public int error;
        public String message;
    }

    public static class LoginResponse extends BaseResponse {
        public Member member;
        public static class Member {
            public String id;
            public String session_key;
        }
    }

    public static class SoftStartResponse extends BaseResponse {
        public int version;
        public String manifest;
    }

    public static class SoftUpdateResponse extends BaseResponse {
        public Soft soft;

        public static class Soft {
            public int id;
        }
    }

    public static class GameInfoResponse extends BaseResponse {
        public Game game;
        public static class Game {
            public String title;
            public List<GameFile> files;
        }
        public static class GameFile {
            public String file_id;
            public int status;
            public int cloud;
            public int cloud_state;
        }
    }

    public static class LobbyResponse extends BaseResponse {
        public Lobby lobby;
        public static class Lobby {
            public String id;
        }
    }

    public static class CredentialResponse extends BaseResponse {
        public NetplayCredential credential;

        public static class NetplayCredential {
            public String id;
            public boolean vpn;
            public String cert;
            public String login;
            public String password;
        }
    }

    public static class JoinResponse extends BaseResponse {
        public String redirect_url;
        public Lobby lobby;

        // Everything RomStation returns for join_lobby.php is nested under
        // "lobby" - there is no top-level stream/controller/credential/cloud/
        // members on the response itself.
        public static class Lobby {
            public String id;
            public String title;
            public String host_ip;
            public boolean is_host;
            public String ws_server;
            public List<LobbyMember> members;
            public Cloud cloud;
        }

        public static class Stream {
            public String uri;
        }

        public static class Controller {
            // Only meaningful inside Cloud - identifies this session's virtual
            // controller router on the shared cloud relay (NOT the same as any
            // individual member's controller id/key below).
            public int id;
            public int ports;
            public Server server;
            public static class Server {
                public String hostname;
                public int port;
            }
        }

        public static class Cloud {
            public Stream stream;
            public Controller controller;
        }

        public static class LobbyMember {
            public String id;
            public int member_id;
            public boolean is_host;
            public int controller_port;
            public int controller_id;
            public int controller_key;
            public MemberController controller;
        }

        public static class MemberController {
            public int port;
            public int id;
            public int key;
        }
    }

    public static class LobbyUpdateResponse extends BaseResponse {
        public long last_update;
        public List<LobbyAction> actions;

        public static class LobbyAction {
            public String name;
            public Object value;
        }
    }
}