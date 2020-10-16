package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.PostDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.service.ICommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instagram/api/v1/user")
public class CommentController {

    public ICommentService commentService;

    public CommentController(ICommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/{user_id}/post/{post_id}/comment")
    public ResponseDTO createComment(@RequestBody CommentDTO commentDTO, @PathVariable("user_id") Long user_id,
                                     @PathVariable("post_id") Long post_id){
                return commentService.createComment(commentDTO, user_id, post_id);
    }


    @GetMapping("/{user_id}/post/{post_id}/comment")
    public ResponseDTO getAllComment(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        return commentService.getAllComment(user_id, post_id);
    }



    @PutMapping("/{user_id}/post/{post_id}/comment/{comment_id}")
    public ResponseDTO updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Long comment_id){
        return commentService.updateComment(commentDTO, comment_id);
    }


    @DeleteMapping("/{user_id}/post/{post_id}/comment/{comment_id}")
    public ResponseDTO deleteComment(@PathVariable("comment_id") Long comment_id){
        return commentService.deleteComment(comment_id);
    }

    @PostMapping("/{user_id}/post/{post_id}/comment/{comment_id}/reply")
    public ResponseDTO replyToComment(@RequestBody CommentDTO commentDTO, @PathVariable Long comment_id,
                                      @PathVariable("user_id") Long user_id){
        return commentService.replyToComment(commentDTO, comment_id, user_id);
    }

    @PutMapping("/{user_id}/post/{post_id}/comment/{comment_id}/reply/{reply_id}")
    public ResponseDTO updateReplyToComment(@RequestBody CommentDTO commentDTO, @PathVariable Long comment_id,
                                      @PathVariable("user_id") Long user_id,
                                      @PathVariable("reply_id") Long reply_id){
        return commentService.updateReplyToComment(commentDTO, comment_id, user_id, reply_id);
    }

    @DeleteMapping("/{user_id}/post/{post_id}/comment/{comment_id}/reply/{reply_id}")
    public ResponseDTO deleteReplyToComment(@PathVariable Long comment_id,
                                            @PathVariable("user_id") Long user_id,
                                            @PathVariable("reply_id") Long reply_id){
        return commentService.deleteReplyToComment(reply_id, comment_id, user_id);
    }


    @GetMapping("/{user_id}/post/{post_id}/comment/{comment_id}")
    public ResponseDTO getCommentAndReplies(@PathVariable("comment_id") Long comment_id){
        return commentService.getCommentAndReplies(comment_id);
    }

}
