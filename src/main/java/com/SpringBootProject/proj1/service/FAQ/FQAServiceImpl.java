package com.SpringBootProject.proj1.service.FAQ;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SpringBootProject.proj1.Entitys.FAQ;
import com.SpringBootProject.proj1.Entitys.Product;
import com.SpringBootProject.proj1.Repositry.FAQRepository;
import com.SpringBootProject.proj1.Repositry.ProductRepository;
import com.SpringBootProject.proj1.dto.FAQDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FQAServiceImpl implements FAQService{
    private final FAQRepository faqRepository;
    private final ProductRepository productRepository;
    public FAQDto postFAQ(Long productId,FAQDto faqDto){
        Optional<Product> optionalProduct= productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            FAQ faq=new FAQ();
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());
            faq.setQuestion(faqDto.getQuestion());
            return faqRepository.save(faq).getFAQDto();
        }
        return null;
    }
    
}
