# OWNER ACTION CHECKLIST: Module 08

## 1. Permissions Needed
- No new Android Manifest permissions needed directly for Room.
- Standard storage accesses exist under current models.

## 2. Room Dependencies Needed
- `androidx.room:room-runtime:2.6.1` (Already present)
- `androidx.room:room-ktx:2.6.1` (Already present)
- `androidx.room:room-compiler:2.6.1` (Already present, via KAPT)

## 3. Manual Setup Needed
- None. Room Database is configured for fallbackToDestructiveMigration during prototype phases. No manual schema scripts needed yet.

## 4. Future Upgrade Options
- Full Vector Engine database upgrade for similarity embeddings.
- Automatic cloud backup synchronization via Firebase or Google Drive (if user opts in to cloud features later).
- Integration with LLM provider for zero-shot intelligent memory extraction.
