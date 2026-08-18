# Walkthrough - Version Bump and Session Fix

I have addressed the "Application Update Required" error and the session persistence issue.

## Changes Made

### Version Bump
- **`RomStationApi.java`**: Incremented the version parameter `v` and `soft_id` from `229` to `240`.
    - **Reason**: The server returned `error: -87` which indicates that the client version is outdated. Bumping to `240` (corresponding to RomStation 2.4.0+) should resolve this.

### Session Persistence
- **`RomStationApi.java`**: Made `memberId` and `sessionKey` **static** fields.
    - **Reason**: `MainActivity` performs the login in the background. If the user starts a game before the login completes, or if the activities switch, the session data was being lost or passed as null. Static fields ensure that once the session is set, it is available globally within the app process.
- **`MainActivity.java` & `GameActivity.java`**: Simplified to use the shared static session in `RomStationApi`.

### Auth Payload Correction
- **`RomStationApi.java`**: Ensured that `member_id` and `member_session` are properly formatted in the encrypted `auth` JSON for lobby operations. `member_id` is now sent as an `Integer` to match the original client's behavior.

## How to Verify

1. Deploy the app.
2. Monitor Logcat:
   `adb logcat RequestNetwork:D MainActivity:D GameActivity:D RomStationApi:D *:S`
3. Check the response for `create_lobby`. It should no longer be `{"error":-87}`.
4. Verify that `GameActivity` logs show the correct `member_id` and `session_key` (if login was successful) in the "Login Auth JSON" for lobby calls.

> [!NOTE]
> If you still see `error: -6` on login, it might be due to the `PHPSESSID` expiring or an IP mismatch between the WebView and the native request. However, the version bump and session fix should allow the app to attempt game initialization more reliably.
