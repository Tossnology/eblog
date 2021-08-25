package com.example.eblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eblog.domain.MUserMessage;
import com.example.eblog.service.MUserMessageService;
import com.example.eblog.mapper.MUserMessageMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class MUserMessageServiceImpl extends ServiceImpl<MUserMessageMapper, MUserMessage>
    implements MUserMessageService{

}




