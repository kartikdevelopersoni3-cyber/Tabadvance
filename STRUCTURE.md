# STRUCTURE.md

## PERMISSION RULE
If a module requires Android permissions, API keys, accessibility access, battery optimization exemption, overlay permission, microphone permission, notification permission, or any manual device setup:
1. Generate the code first.
2. Generate setup instructions.
3. Do NOT require the owner to grant permissions during development.
4. Permissions are granted only during testing/install phase.
5. Never block module generation because permissions are not yet granted.
