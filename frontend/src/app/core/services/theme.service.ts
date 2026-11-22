import { Injectable, signal } from '@angular/core';
export type Theme = 'light' | 'dark';
@Injectable({ providedIn: 'root' })
export class ThemeService {
  readonly theme = signal<Theme>(this.initialTheme());
  constructor() { this.apply(this.theme()); }
  toggle(): void { this.set(this.theme() === 'light' ? 'dark' : 'light'); }
  set(theme: Theme): void {
    this.theme.set(theme);
    // Persist the explicit choice so refreshes do not unexpectedly change the user's theme.
    try { localStorage.setItem('roomsync-theme', theme); } catch { /* Storage can be unavailable in private contexts. */ }
    this.apply(theme);
  }
  private initialTheme(): Theme {
    try {
      const saved = localStorage.getItem('roomsync-theme');
      if (saved === 'light' || saved === 'dark') return saved;
    } catch { /* Fall through to OS preference. */ }
    return typeof matchMedia !== 'undefined' && matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
  }
  private apply(theme: Theme): void {
    if (typeof document !== 'undefined') document.documentElement.dataset['theme'] = theme;
  }
}
