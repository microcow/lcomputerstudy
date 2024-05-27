package com.lcomputerstudy.testmvc.vo.java;

public class Reply {
private String content;
private String date;
private String writer;
private String b_idx;
private String r_idx;
private int depth;
private int grpord;
private int p_post; // b_idx값이 왜래키로 설정되어있어서 굳이 p_post값은 안만들었어도 됐을듯


	public int getDepth() {
	return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public int getGrpord() {
		return grpord;
	}
	public void setGrpord(int grpord) {
		this.grpord = grpord;
	}
	public int getP_post() {
		return p_post;
	}
	public void setP_post(int p_post) {
		this.p_post = p_post;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getB_idx() {
		return b_idx;
	}
	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}
	public String getR_idx() {
		return r_idx;
	}
	public void setR_idx(String r_idx) {
		this.r_idx = r_idx;
	}


}
