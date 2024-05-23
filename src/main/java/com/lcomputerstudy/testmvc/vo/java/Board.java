package com.lcomputerstudy.testmvc.vo.java;

public class Board {
	private String title;
	private String content;
	private String date;
	private String writer;
	private String p_posttitle;
	private int idx;
	private int b_idx;
	private int u_idx;
	private int view;
	private int rownum;
	private int p_post; // 부모 게시글
	private int depth;
	
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getP_posttitle() {
		return p_posttitle;
	}
	public void setP_posttitle(String p_posttitle) {
		this.p_posttitle = p_posttitle;
	}
	public int getP_post() {
		return p_post;
	}
	public void setP_post(int p_post) {
		this.p_post = p_post;
	}
	public int getU_idx() {
		return u_idx;
	}
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getContent() {
		return content;
	}
	public int getB_idx() {
		return b_idx;
	}
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public void upView() {
		this.view += 1;
	}
}

/* 1차로 할것
 계층형 게시판
- 제목, 내용, 조회수, 작성자, 작성일시
- 등록, 수정, 삭제, 목록, 보기
- 테이블 설계
- BoardService, BoardDao, Board 추가 필요
- 기존 사용자 관리 서블릿에 추가
- MVC 로 개발 */