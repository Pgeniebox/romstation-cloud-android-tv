# Implementation Plan - Fix Login Handshake and Protocol Parameters

The login is currently failing with `error: -8` and returning `member_id: 0`. This is likely because I used the actual `member_id` in the initial handshake auth JSON, whereas the RomStation protocol expects `member_id: 0` for the initial identification via `PHPSESSID`. Additionally, I will separate the `SOFT_ID` from the `VERSION` parameter to match the user's observation that the server reports version `229`, while also ensuring we use a high enough `v` value to avoid the "Update Required" error.

## User Review Required

> [!IMPORTANT]
> I am reverting the initial `login` handshake to use `member_id: 0` as specified in the workflow.
> I will also try using `SOFT_ID = 229` (matching the user's `start.php` output) while keeping `v = 292` in the URL to satisfy the version check.
> I will remove `v`, `os`, and `arch` from the POST body and only keep them in the URL query string, as per the original client's behavior.

## Proposed Changes

### [app] component

#### [MODIFY] [RomStationApi.java](file:///E:/try/app/src/main/java/com/world/cloudxsolution/RomStationApi.java)
- Set `SOFT_ID = 229`.
- Keep `VERSION = "292"`.
- In `login`, hardcode `member_id: 0` in the `auth` JSON.
- In `createLobby` and `joinLobby`, use the `static memberId` (which will be updated if login succeeds).
- Ensure `v`, `os`, and `arch` are **not** included in the `params` HashMap for POST requests (they are already added to the URL).

## Verification Plan

### Automated Tests
- Build and deploy.
- Check Logcat for `Response [login]`. If it returns your actual `member_id` (e.g. `2251896`) and `error: 1`, then the handshake is fixed.
- Check `Response [create_lobby]`. It should now succeed once the session is established.

### Manual Verification
- Confirm that `GameActivity` proceeds past lobby creation.
