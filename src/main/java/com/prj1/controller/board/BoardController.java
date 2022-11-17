package com.prj1.controller.board;

import com.prj1.domain.board.BoardDto;
import com.prj1.domain.board.PageInfo;
import com.prj1.service.board.BoardSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("board")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BoardController {

	@Autowired
	private BoardSerivce service;

	@GetMapping("register")
	public void register() {
	}

	@PostMapping("register")
	public String register(
			BoardDto board,
			MultipartFile[] files,
			RedirectAttributes rttr) {
		int cnt = service.register(board, files);

		if (cnt == 1) {
			rttr.addFlashAttribute("message", "새 게시물이 등록되었습니다.");
		} else {
			rttr.addFlashAttribute("message", "새 게시물이 등록되지 않았습니다.");
		}
		return "redirect:/board/list";
	}

	@GetMapping("list")
	public void list(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "t", defaultValue = "all") String type,
			@RequestParam(name = "q", defaultValue = "") String keyword,
			PageInfo pageInfo,
			Model model) {
		List<BoardDto> list = service.listBoard(page, type, keyword, pageInfo);

		model.addAttribute("boardList", list);
	}
	@GetMapping("get")
	public void get(
			@RequestParam(name = "id") int id,
			Model model, Authentication authentication) {
		String username=null;
		if(authentication!=null){
            username=authentication.getName();
		}
		BoardDto board = service.get(id, username);
		model.addAttribute("board", board);
	}
	@GetMapping("modify")
	@PreAuthorize("@boardSecurity.checkWriter(authentication.name,#id)")
	public void modify(int id, Model model) {
		BoardDto board = service.get(id);
		model.addAttribute("board", board);
	}
	@PostMapping("modify")
	@PreAuthorize("@boardSecurity.checkWriter(authentication.name,#board.id)")
	public String modify(BoardDto board,
						 RedirectAttributes rttr,
						 @RequestParam(name="removeFiles",required = false) List<String> removeFiles,
						 @RequestParam("files") MultipartFile[] addFiles) {
		if(removeFiles != null){
			for(String name :removeFiles){
				System.out.println(name);
			}
		}
		int cnt = service.update(board,addFiles, removeFiles);
		if (cnt == 1) {
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되었습니다.");
		} else {
			rttr.addFlashAttribute("message", board.getId() + "번 게시물이 수정되지 않았습니다.");
		}

		return "redirect:/board/list";
	}

	@PostMapping("remove")
	@PreAuthorize("@boardSecurity.checkWriter(authentication.name,#id)")
	public String remove(int id,RedirectAttributes rttr) {
		int cnt = service.remove(id);

		if (cnt == 1) {
			// id번 게시물이 삭제되었습니다.
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되었습니다.");
		} else {
			// id번 게시물이 삭제되지 않았습니다.
			rttr.addFlashAttribute("message", id + "번 게시물이 삭제되지 않았습니다.");
		}
		return "redirect:/board/list";
	}
	// 좋아요기능
	@PutMapping("like")
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public Map<String, Object> like(@RequestBody Map<String, String> req, Authentication authentication){

		Map<String,Object> result =  service.updateLike(req.get("boardId"),authentication.getName());
		return result;
	}

}










