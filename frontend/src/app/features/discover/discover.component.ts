import { Component, inject, signal } from '@angular/core';
import { CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ListingApiService } from '../../core/services/listing-api.service';
import { Listing } from '../../core/models/listing.model';

@Component({ selector: 'rs-discover', standalone: true, imports: [CurrencyPipe, NgFor, NgIf, FormsModule], template: `
<section class="section-head"><div><p class="eyebrow">FIND YOUR PEOPLE</p><h1>Discover homes that <span class="accent">fit your life.</span></h1><p class="muted">Explore shared homes and find roommates who match your lifestyle.</p></div></section>
<div class="searchbar"><label class="sr-only" for="city">City</label><input id="city" [(ngModel)]="city" placeholder="Search city, e.g. Pune"><button class="primary" (click)="load()">Search homes</button></div>
<p class="muted" *ngIf="loading()">Finding homes…</p><p class="error" *ngIf="error()">{{error()}}</p>
<div class="listing-grid"><article class="listing-card" *ngFor="let item of listings()"><div class="listing-art"><span>RoomSync home</span><span class="match">Lifestyle-first</span></div><div class="listing-body"><div class="row"><h3>{{item.title}}</h3><strong>{{item.monthlyRent | currency:'INR':'symbol':'1.0-0'}}<small>/ person</small></strong></div><p class="muted">{{item.locality}}, {{item.city}}</p><div class="tags"><span>{{item.availableSlots}} spot(s) available</span><span>Shared living</span></div></div></article></div>
<p class="empty" *ngIf="!loading() && !error() && listings().length===0">No homes found yet. Try another city or add the first listing through the API.</p>
` })
export class DiscoverComponent {
  private readonly api = inject(ListingApiService);
  city = 'Pune'; readonly listings = signal<Listing[]>([]); readonly loading = signal(false); readonly error = signal('');
  constructor() { this.load(); }
  load(): void {
    const city = this.city.trim(); if (!city) { this.error.set('Enter a city to search.'); return; }
    this.loading.set(true); this.error.set('');
    this.api.search(city).subscribe({ next: result => { this.listings.set(result.content); this.loading.set(false); }, error: () => { this.error.set('Could not load listings. Check that the API and dependencies are running.'); this.loading.set(false); } });
  }
}
