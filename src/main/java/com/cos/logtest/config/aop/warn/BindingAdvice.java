package com.cos.logtest.config.aop.warn;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@Aspect
public class BindingAdvice {
	
	//로그 만들기 sysout과 비슷하지만 log의 레벨이 있다
	private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

	
	//정규표현식
	@Around("execution(* com.cos.logtest.controller..*Controller.*(..))")
	public Object bindingPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		//binding 찾기
		Object[] args = proceedingJoinPoint.getArgs();
		
		//log를 찍기 위해서 추가한 변수들
		String type = proceedingJoinPoint.getSignature().getDeclaringTypeName()+" : ";
		String methodName = proceedingJoinPoint.getSignature().getName()+"() ";
		
		for(Object arg : args) {
			if(arg instanceof BindingResult) {
				//instanceof는 type을 비교하는것
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					
					Map<String, String> errorMap = new HashMap<>();
					for(FieldError error : bindingResult.getFieldErrors()) {
						//여기서 센트리 오류 던지면 됨
						//-----------------------
						log.info("인포 나오나");
						log.debug("디버그 나오나");
						log.error("에러 나오나");
						log.warn(type+methodName+error.getDefaultMessage()); //콘솔에만 찍힘
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
				}
			}
		}
		return proceedingJoinPoint.proceed();
	}
}
