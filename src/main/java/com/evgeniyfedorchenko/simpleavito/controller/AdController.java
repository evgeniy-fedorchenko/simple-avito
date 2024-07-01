package com.evgeniyfedorchenko.simpleavito.controller;

import com.evgeniyfedorchenko.simpleavito.dto.*;
import com.evgeniyfedorchenko.simpleavito.service.AdService;
import com.evgeniyfedorchenko.simpleavito.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Ad> addAd(@RequestBody @Valid CreateOrUpdateAd properties, @RequestPart MultipartFile image) {
        return ResponseEntity.ok(adService.addAd(properties, image));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable int id) {
        return ResponseEntity.ok(adService.getAds(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable int id) {
        adService.removeAd(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable int id, @RequestBody @Valid CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok(adService.updateAds(id, createOrUpdateAd));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<Ads> getAdsMe() {
        return ResponseEntity.ok(adService.getAdsMe());
    }

    @PatchMapping(
            path = "/{id}/" + IMAGE_PATH_SEGMENT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<byte[]> updateImage(@PathVariable int id, @RequestPart MultipartFile image) {
        return ResponseEntity.ok(adService.updateImage(id, image));
    }



    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<Comments> getComments(@PathVariable int id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }

    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable int id,
                                              @RequestBody @Valid CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateComment));
    }

    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable int adId, @PathVariable int commentId) {
        commentService.deleteComment(adId, commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable int adId,
                                                 @PathVariable int commentId,
                                                 @RequestBody @Valid CreateOrUpdateComment comment) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, comment));

    }
}
