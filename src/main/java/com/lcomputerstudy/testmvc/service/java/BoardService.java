package com.lcomputerstudy.testmvc.service.java;


import java.util.ArrayList;

import com.lcomputerstudy.testmvc.dao.java.BoardDAO;
import com.lcomputerstudy.testmvc.vo.java.Board;
import com.lcomputerstudy.testmvc.vo.java.Reply;
import com.lcomputerstudy.testmvc.vo.java.Upload;
import com.lcomputerstudy.testmvc.vo.java.User;

public class BoardService {
	
	private static BoardService service = null;
	private static BoardDAO dao = null;
	
	
	public static BoardService getInstance(){
		if(service == null) {
			service = new BoardService();
			dao = BoardDAO.getInstance();
		}
		return service;
	
	}
	public int insertBoard(Board board) {
		return dao.insertBoard(board);
	}
	public ArrayList<Board> getPostList(int page) {
		return dao.getPostList(page);
	}
	public int getPostCount() {
		return dao.getPostCount();
	}
	public Board getPost(String b_idx) {
		return dao.getPost(b_idx);
	}
	public void updateView(String b_idx) {
		dao.updateView(b_idx);
	}
	public void deletePost(String b_idx) {
		dao.deletePost(b_idx);
	}
	public void changePost(Board changeBoard) {
		dao.changePost(changeBoard);
	}
	public void setp_post() {
		dao.setp_post();
	}
	public void setGrpord() {
		dao.setGrpord();
	}
	
	public void setReplyGrpord(int p_post, int grpord){
		dao.setReplyGrpord(p_post, grpord);
	}
	public void insertReply(Reply reply) {
		dao.insertReply(reply);
	}
	public ArrayList<Reply> getReplyList(String b_idx) {
		return dao.getReplyList(b_idx);
	}
	public void setCommentGrpord(int p_post, int grpord){
		dao.setCommentGrpord(p_post, grpord);
	}
	public Reply getReply(String r_idx) {
		return dao.getReply(r_idx);
	}
	public void changeReply(Reply changeReply){
		dao.changeReply(changeReply);
	}
	public void deleteReply(Reply changeReply){
		dao.deleteReply(changeReply);
	}
	public ArrayList<Board> SelectBoard(String search, String content, int page){
		return dao.SelectBoard(search, content, page);
	}
	public void insertFile(Upload file){
		dao.insertFile(file);
	}
	public Upload getUploadFile(String b_idx) {
		return dao.getUploadFile(b_idx);
	}
}
