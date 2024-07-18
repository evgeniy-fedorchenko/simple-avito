package com.evgeniyfedorchenko.simpleavito.controller;

import com.evgeniyfedorchenko.simpleavito.dto.*;
import com.evgeniyfedorchenko.simpleavito.service.AdService;
import com.evgeniyfedorchenko.simpleavito.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
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
                                    @RequestPart MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(properties, image));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable @Positive long id) {
        return ResponseEntity.of(adService.getAds(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable @Positive long id) {
        return adService.removeAd(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable @Positive long id, @RequestBody @Valid CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.of(adService.updateAds(id, createOrUpdateAd));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<Ads> getAdsMe() {
        return ResponseEntity.of(adService.getAdsMe());
    }

    @PatchMapping(
            path = "/{id}/" + IMAGE_PATH_SEGMENT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<byte[]> updateImage(@PathVariable @Positive long id, @RequestPart MultipartFile image) {
        return ResponseEntity.of(adService.updateImage(id, image));
    }

    @GetMapping(path = "/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable @Positive long id) {
        return ResponseEntity.of(adService.getImage(id));
    }



    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable("id") @Positive long adId) {
        return ResponseEntity.of(commentService.getComments(adId));
    }

    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable("id") @Positive long adId,
                                              @RequestBody @Valid CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.of(commentService.addComment(adId, createOrUpdateComment));
    }

    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable @Positive long adId,
                                              @PathVariable @Positive long commentId) {
        return commentService.deleteComment(adId, commentId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable @Positive long adId,
                                                 @PathVariable @Positive long commentId,
                                                 @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.of(commentService.updateComment(adId, commentId, comment));

    }
}
