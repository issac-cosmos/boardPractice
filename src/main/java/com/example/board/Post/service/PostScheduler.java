package com.example.board.Post.service;

import com.example.board.Post.domain.Post;
import com.example.board.Post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional
public class PostScheduler {
    private final PostRepository postRepository;

    public PostScheduler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
//  cron의 각 자리는 초 분 시간 일 월 요일 을 의미
//  예를들어) * * * * * * : 매일 매분 매초 마다
//  예를들어) 0 0 * * * * : 매일 매분 0분 0초 마다
//  예를들어) * * 11 * * * : 매일 11시에
//  예를들어) 0 0/1 * * * * : 매일 매시 1분 마다
//  예를들어) 0 1 * * * * : 매일 매시 1분에
    @Scheduled(cron = "0 0/1 * * * *")
    public void postSchedule(){
        System.out.println("===예약글쓰기 스케줄러 시작===");
        Page<Post> posts = postRepository.findAllByAppointment(Pageable.unpaged(), "Y");
        LocalDateTime now = LocalDateTime.now();
        for(Post p : posts){
            if(p.getAppointmentTime().isBefore(now)){
                p.updateAppointment("N");
            }
        }
    }
}
