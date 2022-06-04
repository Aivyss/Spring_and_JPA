package com.jpabook.jpashop.template;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class SmsTemplate {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sms_template_key")
	private Long id;
	
	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "study_key", nullable = false)
	private Study study;
	
	@Column(name = "template_title")
	private String title;
	@Column(name = "message", nullable = false)
	private String message;
	
	private SmsTemplate(Long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * 비영속 + no identifier 상태의 template entity를 생성
	 * @param study 과제
	 * @param title 템플릿 타이틀
	 * @param message sms문자 템플릿
	 * @return 비영속 상태 엔티티 객체
	 */
	public static SmsTemplate newStudySmsTemplate(Study study, String title, String message) {
		return new SmsTemplate(null, title, message);
	}
	
	public void setStudy(Study study) {
		this.study = study;
	}
	
	/**
	 * 엔티티의 데이터를 업데이트
	 * @param title 템플릿 타이틀
	 * @param message sms 문자 템플릿
	 */
	public void updateTemplate(String title, String message) {
		this.title = title;
		this.message = message;
	}
}
