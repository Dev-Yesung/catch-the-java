package playground.spring.spring_lab.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class MvcTestController {

	private final AtomicInteger bookmarkId = new AtomicInteger();
	private final List<Bookmark> bookmarks = new ArrayList<>();

	@GetMapping
	public ResponseEntity<String> getTestMsg() {
		return ResponseEntity.ok("Hello World");
	}

	@PostMapping
	public ResponseEntity<String> postTestMsg(
		@RequestParam("title") String title,
		@RequestParam("content") String content) {
		System.out.println("Title: " + title + ", Content: " + content);

		return ResponseEntity.ok(String.format("title=%s, content=%s", title, content));
	}

	@GetMapping("/ajax")
	public ResponseEntity<Msg> ajaxTestMsg() {
		Msg msg = new Msg("인사", "하이루", "바이루");
		System.out.println(msg);
		return ResponseEntity.ok(msg);
	}

	@PostMapping("/bookmarks")
	public ResponseEntity<Bookmark> createBookmark(
		@RequestBody BookmarkRequest bookmark
	) {
		int newId = bookmarkId.incrementAndGet();
		Bookmark newBookmark = new Bookmark(newId, bookmark.name(), bookmark.url());
		bookmarks.add(newBookmark);

		return ResponseEntity.ok(newBookmark);
	}

	@GetMapping("/bookmarks")
	public ResponseEntity<List<Bookmark>> getBookmarks() {
		return ResponseEntity.ok(bookmarks);
	}
}
