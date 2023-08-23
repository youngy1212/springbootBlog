package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
public class BoardService {
	
	@Autowired
	private  BoardRepository boardRepository;
	
	@Autowired
	private  ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Object 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void 글삭제하기(int id) {
		 boardRepository.deleteById(id);
	}

	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		//1. 영속화
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 수정 실패 : 글을 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시(Sevice가 종료될때 트랜잭션도 종료.
		//이때 더티체킹 -자동업데이트가 됨 db flush
		

	}

//	@Transactional
//	public void 댓글쓰기(User user, int boardId, Reply requestReply) {
//		Board board = boardRepository.findById(boardId).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 수정 실패 : 게시글 ID 을 찾을 수 없습니다.");
//		});	
//		requestReply.setUser(user);
//		requestReply.setBoard(board);	
//		replyRepository.save(requestReply);
//	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

		//2번 방법	(영속화)
//		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
//			return new IllegalArgumentException("아이디 ID 을 찾을 수 없습니다.");
//		});
//		
//		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
//			return new IllegalArgumentException("게시글 ID 을 찾을 수 없습니다.");
//		});	
		
		//1번 방법
//		Reply reply = new Reply();
//		reply.update(user, board, replySaveRequestDto.getContent());
		//build 사용 이외에도 Reply에 함수를 만들어서 호출하여 update 가능
		
		//3번방법  @Query 사용
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService : "+result);
	}
}
