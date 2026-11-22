package com.roomsync.listing;

import java.math.BigDecimal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController @RequestMapping("/api/v1/listings")
public class ListingController {
    private final ListingService service;
    public ListingController(ListingService service) { this.service=service; }
    public record CreateListingRequest(@NotBlank @Size(max=120) String title, @NotBlank @Size(max=80) String city, @NotBlank @Size(max=120) String locality, @NotNull @DecimalMin("1.00") @DecimalMax("10000000.00") BigDecimal monthlyRent, @NotNull @Min(1) @Max(20) Integer availableSlots) {}
    @GetMapping public Page<ListingResponse> search(@RequestParam @NotBlank @Size(max=80) String city, @RequestParam(defaultValue="0") @Min(0) int page, @RequestParam(defaultValue="20") @Min(1) @Max(50) int size) { return service.search(city, page, size); }
    // Auth is intentionally deferred. Protect this endpoint before any public deployment.
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ListingResponse create(@Valid @RequestBody CreateListingRequest r) { return service.create(new Listing(r.title(), r.city(), r.locality(), r.monthlyRent(), r.availableSlots())); }
}
