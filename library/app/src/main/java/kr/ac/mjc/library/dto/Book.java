package kr.ac.mjc.library.dto;

public class Book {
    //책 제목
    private String titleStatement;
    //책 저자
    private String author;
    //책 표지 이미지 URL
    private String thumbnailUrl;

    public String getTitleStatement() {
        return titleStatement;
    }

    public void setTitleStatement(String titleStatement) {
        this.titleStatement = titleStatement;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
