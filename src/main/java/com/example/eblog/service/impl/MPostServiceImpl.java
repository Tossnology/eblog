package com.example.eblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eblog.domain.MPost;
import com.example.eblog.service.MPostService;
import com.example.eblog.mapper.MPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class MPostServiceImpl extends ServiceImpl<MPostMapper, MPost>
    implements MPostService{

}




