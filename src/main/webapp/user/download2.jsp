<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.servlet.ServletOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    try {
        // check.jsp로부터 넘어온 값(fileName의 값)을 구함
        String fileName = request.getParameter("fileName");

        if (fileName == null || fileName.isEmpty()) {
            throw new IOException("파일 이름이 제공되지 않았습니다.");
        }

        // 다운로드 할 파일의 경로를 구하고 File 객체 생성
        ServletContext context = getServletContext();
        String downloadPath = context.getRealPath("upload");
        String filePath = downloadPath + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("파일이 존재하지 않습니다: " + filePath);
        }

        // MIMETYPE 설정
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);

        // 다운로드 설정 및 한글 파일명 깨지는 것 방지
        String encoding = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encoding + "\"");

        // 요청 파일을 읽어서 클라이언트에 전송
        FileInputStream in = new FileInputStream(file);
        ServletOutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        outStream.flush();
        outStream.close();
        in.close();
    } catch (IOException e) {
        out.println("파일 다운로드 중 오류가 발생했습니다: " + e.getMessage());
    }
%>