export interface Listing {
  id: number; title: string; city: string; locality: string;
  monthlyRent: number; availableSlots: number; createdAt: string;
}
export interface PageResponse<T> {
  content: T[]; totalElements: number; totalPages: number; number: number; size: number;
}
