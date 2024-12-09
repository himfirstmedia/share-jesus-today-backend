package com.himfirst.vidshare.content.video;

import com.himfirst.vidshare.exceptions.ApiResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/video")
@CrossOrigin
@Slf4j
public class VideoController {
    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<VideoModel> uploadVideo(@RequestParam("file") MultipartFile file,
                                                  @RequestParam(required = false) String title,
                                                  @RequestParam(required = false) String caption, Principal principal) {

        if (file.isEmpty()) {
            throw new ApiResponseException("Failed to upload video. File is empty.");
        }

        if (!Objects.requireNonNull(file.getContentType()).startsWith("video/") ||
                (!Objects.equals(file.getContentType(), "video/mp4") &&
                        !Objects.equals(file.getContentType(), "video/quicktime"))) {
            throw new ApiResponseException("Failed to upload video. Only MP4 and MOV files are allowed.");
        }

        return new ResponseEntity<>(videoService.uploadVideo(file, title, caption, principal.getName()), HttpStatus.OK);

    }

    @GetMapping("/public/all")
    public ResponseEntity<List<VideoModel>> getAllVideos() {
        return new ResponseEntity<>(videoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/public/all-page")
    public ResponseEntity<VideoPage> getVideosByPage(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "100") int size,
                                                     @RequestParam(defaultValue = "createdTimestamp") String sortBy,
                                                     @RequestParam(defaultValue = "DESC") String sortOrder) {
        return ResponseEntity.ok(videoService.findAll(page, size, sortBy, sortOrder));
    }

    @GetMapping("/public/person/{personId}")
    public ResponseEntity<VideoPage> grtVideosByPersonId(@RequestParam UUID personId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "100") int size,
                                                  @RequestParam(defaultValue = "createdTimestamp") String sortBy,
                                                  @RequestParam(defaultValue = "DESC") String sortOrder ) {
        return ResponseEntity.ok(videoService.findByPersonId(personId, page, size, sortBy, sortOrder));
    }

    @GetMapping("/my-videos")
    public ResponseEntity<VideoPage> myVideos(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "100") int size,
                                                  @RequestParam(defaultValue = "createdTimestamp") String sortBy,
                                                  @RequestParam(defaultValue = "DESC") String sortOrder, Principal principal) {
        return ResponseEntity.ok(videoService.myVideos(principal.getName(), page, size, sortBy, sortOrder));
    }




//    @GetMapping("/public/stream/{videoId}")
//    public void streamVideo(@PathVariable UUID videoId, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Video video = videoService.findById(videoId);
//        Blob blob = fileService.getBolb(video.getUrl());
//
//        if (blob == null) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
//
//        String range = request.getHeader("Range");
//        long fileSize = blob.getSize();
//        response.setContentType("video/quicktime");
//        response.setHeader("Accept-Ranges", "bytes");
//
//        try (ReadableByteChannel channel = blob.reader();
//             OutputStream outputStream = response.getOutputStream()) {
//
//            if (range == null) {
//                // Full content response
//                response.setStatus(HttpServletResponse.SC_OK);
//                streamData(channel, outputStream, fileSize);
//            } else {
//                // Partial content response
//                String[] ranges = range.replace("bytes=", "").split("-");
//                long start = Long.parseLong(ranges[0]);
//                long end = ranges.length > 1 ? Long.parseLong(ranges[1]) : fileSize - 1;
//
//                if (start >= fileSize || end >= fileSize) {
//                    response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
//                    response.setHeader("Content-Range", "bytes */" + fileSize);
//                    return;
//                }
//
//                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//                response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
//
//                blob.reader().seek(start);
//                streamData(channel, outputStream, end - start + 1);
//            }
//        }
//    }
//
//    private void streamData(ReadableByteChannel channel, OutputStream outputStream, long limit) throws IOException {
//        ByteBuffer buffer = ByteBuffer.allocate(8192); // Larger buffer for efficiency
//        long bytesWritten = 0;
//
//        while (channel.read(buffer) > 0 && bytesWritten < limit) {
//            buffer.flip();
//            int bytesToWrite = (int) Math.min(buffer.remaining(), limit - bytesWritten);
//            outputStream.write(buffer.array(), 0, bytesToWrite);
//            bytesWritten += bytesToWrite;
//            buffer.clear();
//        }
//    }


}
