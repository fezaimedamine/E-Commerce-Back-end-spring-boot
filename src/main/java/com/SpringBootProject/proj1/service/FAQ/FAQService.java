package com.SpringBootProject.proj1.service.FAQ;

import com.SpringBootProject.proj1.dto.FAQDto;

public interface FAQService {
    FAQDto postFAQ(Long productId,FAQDto faqDto);
    
}
