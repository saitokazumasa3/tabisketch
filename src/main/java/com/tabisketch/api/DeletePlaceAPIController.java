package com.tabisketch.api;

import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.service.IDeletePlaceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delete-place/{id}")
public class DeletePlaceAPIController {
    private final IDeletePlaceService deletePlaceService;

    public DeletePlaceAPIController(final IDeletePlaceService deletePlaceService) {
        this.deletePlaceService = deletePlaceService;
    }

    @PostMapping
    public void post(final @PathVariable int id) throws DeleteFailedException {
        this.deletePlaceService.execute(id);
    }
}
