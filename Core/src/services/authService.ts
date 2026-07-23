import { UserProfile } from '../types';

const AUTH_STORAGE_KEY = 'roohi_os_auth_profile_v1';

const DEFAULT_PROFILE: UserProfile = {
  id: 'usr-guest-001',
  name: 'Roohi Guest User',
  email: 'ritusoniji1983@gmail.com',
  avatarUrl: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=150&q=80',
  authProvider: 'google',
  isAuthenticated: true,
  cloudSyncEnabled: true,
  lastSyncedAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
};

export class AuthService {
  public static getProfile(): UserProfile {
    try {
      const stored = localStorage.getItem(AUTH_STORAGE_KEY);
      if (stored) {
        return JSON.parse(stored);
      }
    } catch {}
    return DEFAULT_PROFILE;
  }

  public static saveProfile(profile: UserProfile): void {
    try {
      localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(profile));
    } catch {}
  }

  public static toggleCloudSync(): UserProfile {
    const profile = this.getProfile();
    profile.cloudSyncEnabled = !profile.cloudSyncEnabled;
    profile.lastSyncedAt = new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    this.saveProfile(profile);
    return profile;
  }
}
