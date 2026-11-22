package com.roomsync.listing;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListingService {
    private final ListingRepository repository;
    public ListingService(ListingRepository repository) { this.repository = repository; }
    @Transactional(readOnly=true)
    @Cacheable(cacheNames="listing-search", key="#city.toLowerCase() + ':' + #page + ':' + #size")
    public Page<ListingResponse> search(String city, int page, int size) {
        return repository.findByCityIgnoreCaseAndActiveTrue(city, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))).map(ListingResponse::from);
    }
    @Transactional
    @CacheEvict(cacheNames="listing-search", allEntries=true)
    public ListingResponse create(Listing listing) { return ListingResponse.from(repository.save(listing)); }
}
