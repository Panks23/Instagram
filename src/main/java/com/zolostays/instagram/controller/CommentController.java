package com.zolostays.instagram.controller;

import com.zolostays.instagram.dto.CommentDTO;
import com.zolostays.instagram.dto.ResponseDTO;
import com.zolostays.instagram.exception.BaseException;
import com.zolostays.instagram.service.ICommentService;
import com.zolostays.instagram.util.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

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
                try{
                    CommentDTO resultCommentDTO = commentService.createComment(commentDTO, user_id, post_id);
                    return Mapper.responseDTOSingle(resultCommentDTO, "You have commented");
                }catch (BaseException baseException){
                    return Mapper.responseDTOSingle(null, baseException.getMessage());
                }
    }


    @GetMapping("/{user_id}/post/{post_id}/comment")
    public ResponseDTO getAllComment(@PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try{
            return Mapper.responseDTO(commentService.getAllComment(user_id, post_id), "You have got all comment");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }



    @PutMapping("/{user_id}/post/{post_id}/comment/{comment_id}")
    public ResponseDTO updateComment(@RequestBody CommentDTO commentDTO,
                                     @PathVariable("user_id") Long user_id,
                                     @PathVariable("comment_id") Long comment_id,
                                     @PathVariable("post_id") Long post_id){
        try{
            return Mapper.responseDTOSingle(commentService.updateComment(commentDTO, user_id, post_id,comment_id),
                        "You have updated your comment");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }


    @DeleteMapping("/{user_id}/post/{post_id}/comment/{comment_id}")
    public ResponseDTO deleteComment(@PathVariable("user_id") Long user_id,
                                     @PathVariable("post_id") Long post_id,
                                     @PathVariable("comment_id") Long comment_id){
        try{
            commentService.deleteComment(user_id, post_id, comment_id);
            return Mapper.responseDTO(new ArrayList<>(), "You have successfully Deleted");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

    @PostMapping("/{user_id}/post/{post_id}/comment/{comment_id}/reply")
    public ResponseDTO reply(@RequestBody CommentDTO commentDTO, @PathVariable Long comment_id,
                                      @PathVariable("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try {
            return Mapper.responseDTOSingle(commentService.reply(commentDTO, comment_id, user_id, post_id),
                    "You have replied");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

    @PutMapping("/{user_id}/post/{post_id}/comment/{comment_id}/reply/{reply_id}")
    public ResponseDTO updateReply(@RequestBody CommentDTO commentDTO, @PathVariable Long comment_id,
                                      @PathVariable("user_id") Long user_id,
                                      @PathVariable("reply_id") Long reply_id,
                                            @PathVariable("post_id") Long post_id){
        try {
           return Mapper.responseDTOSingle(commentService.updateReply(commentDTO, comment_id, user_id, reply_id, post_id),"" +
                    "You have updated your reply");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

    @DeleteMapping("/{user_id}/post/{post_id}/comment/{comment_id}/reply/{reply_id}")
    public ResponseDTO deleteReply(@PathVariable Long comment_id,
                                            @PathVariable("user_id") Long user_id,
                                            @PathVariable("reply_id") Long reply_id,
                                            @PathVariable("post_id") Long post_id){
        try {
            commentService.deleteReply(user_id, post_id, comment_id, reply_id);
            return Mapper.responseDTO(new ArrayList<>(), "You have deleted reply");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }


    @GetMapping("/{user_id}/post/{post_id}/comment/{comment_id}")
    public ResponseDTO getCommentAndReplies(@PathVariable("user_id") Long user_id,
                                            @PathVariable("post_id") Long post_id,
                                            @PathVariable("comment_id") Long comment_id){
        try{
            return Mapper.responseDTO(commentService.getCommentAndReplies(user_id, post_id, comment_id), "You have recieved replies and comment");
        }catch (BaseException baseException){
            return Mapper.responseDTOSingle(null, baseException.getMessage());
        }
    }

}
