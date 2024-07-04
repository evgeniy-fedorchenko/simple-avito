package com.evgeniyfedorchenko.simpleavito.controller;

import com.evgeniyfedorchenko.simpleavito.dto.*;
import com.evgeniyfedorchenko.simpleavito.service.AdService;
import com.evgeniyfedorchenko.simpleavito.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = AdController.BASE_AD_URI)
@AllArgsConstructor
public class AdController {

    public static final String BASE_AD_URI = "/ads";
    public static final String IMAGE_PATH_SEGMENT = "image";
    private final AdService adService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<Ads> getAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> addAd(@RequestPart @Valid CreateOrUpdateAd properties,
                                    @RequestPart MultipartFile image,
                                    Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(properties, image, auth.getName()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable long id) {
        return ResponseEntity.of(adService.getAds(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable long id) {
        return adService.removeAd(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable long id, @RequestBody @Valid CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.of(adService.updateAds(id, createOrUpdateAd));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<Ads> getAdsMe(Authentication auth) {
        return ResponseEntity.of(adService.getAdsMe(auth.getName()));
    }

    @PatchMapping(
            path = "/{id}/" + IMAGE_PATH_SEGMENT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<byte[]> updateImage(@PathVariable long id, @RequestPart MultipartFile image) {
        return adService.updateImage(id, image)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .contentLength(resp.getFirst().length)
                        .contentType(resp.getSecond())
                        .body(resp.getFirst()))
                .orElseGet(() -> ResponseEntity.of(Optional.empty()));
    }



    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable("id") long adId) {
        return ResponseEntity.of(commentService.getComments(adId));
    }

    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable long id,
                                              @RequestBody @Valid CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateComment));
    }

    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable long adId, @PathVariable long commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable long adId,
                                                 @PathVariable long commentId,
                                                 @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, comment));

    }
}
