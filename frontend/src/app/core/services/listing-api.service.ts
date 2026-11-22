import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Listing, PageResponse } from '../models/listing.model';

@Injectable({ providedIn: 'root' })
export class ListingApiService {
  private readonly http = inject(HttpClient);
  // Keep environment-specific API origins in environment files when deployment routing is finalized.
  private readonly baseUrl = '/api/v1/listings';
  search(city: string, page = 0, size = 20): Observable<PageResponse<Listing>> {
    const params = new HttpParams().set('city', city).set('page', page).set('size', size);
    return this.http.get<PageResponse<Listing>>(this.baseUrl, { params });
  }
}
