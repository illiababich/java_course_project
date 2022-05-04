package dsbook;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentText;
    //@ManyToOne
    //private Comment parentComment;
    //@ManyToOne
    //private Book parentBook;

    //@OneToMany(mappedBy = "maincomment", cascade = {CascadeType.ALL}, orphanRemoval = true)
    //@OrderBy("id ASC")
    //@LazyCollection(LazyCollectionOption.FALSE)
    //private List<Comment> reply;
    //@ManyToOne
    //private Comment mainComment;

    private LocalDate dateWritten;

    public Comment() {
    }

//    public Comment(LocalDate dateWritten, String comment, Comment mainComment) {
//        this.dateWritten = dateWritten;
//        this.commentText = comment;
//        this.mainComment = mainComment;
//    }
//
//    public Comment(LocalDate dateWritten, String comment) {
//        this.dateWritten = dateWritten;
//        this.commentText = comment;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public List<Comment> getReply() {
//        return reply;
//    }
//
//    public void setReply(List<Comment> reply) {
//        this.reply = reply;
//    }
//
//    public Comment getMainComment() {
//        return mainComment;
//    }
//
//    public void setMainComment(Comment mainComment) {
//        this.mainComment = mainComment;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateWritten() {
        return dateWritten;
    }

    public void setDateWritten(LocalDate dateWritten) {
        this.dateWritten = dateWritten;
    }

    public String getComment() {
        return commentText;
    }

    public void setComment(String comment) {
        this.commentText = comment;
    }

    @Override
    public String toString() {
        return id +
                ":" + commentText;
    }
}