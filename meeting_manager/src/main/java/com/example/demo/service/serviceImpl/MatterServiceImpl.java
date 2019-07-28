package com.example.demo.service.serviceImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MatterDao;
import com.example.demo.entity.Matter;
import com.example.demo.service.MatterService;

@Service(value = "MatterServiceImpl")
public class MatterServiceImpl implements MatterService {
	@Autowired
	MatterDao MatterDao;

	@Override
	public void save(Matter entity) {
		MatterDao.saveAndFlush(entity);
	}

	@Override
	public List<Matter> findBydeptnoId(String deptnoId) {
		return MatterDao.findByDeptnoId(deptnoId);
	}

	@Override
	public Matter findBypasswordAndmatterid(String ispassword,Integer matterid) {
		return MatterDao.findBypasswordAndmatterid(ispassword,matterid);
		
	}

	@Override
	public Matter findBymatterId(Integer matterId) {
		return MatterDao.findByMatterId(matterId);
	}


	@Override
	public Page<Matter> Paging(Integer id, Integer numberpage, Integer deptno) {
		Pageable pageable=new PageRequest(id,numberpage);
		Page<Matter> users=MatterDao.findAll(new Specification<Matter>() {
			@Override
			public Predicate toPredicate(Root<Matter> root, CriteriaQuery<?> query, CriteriaBuilder cr) {
				Predicate pcPredicate=cr.conjunction();
				if(null!=deptno) {
					pcPredicate.getExpressions().add(cr.equal(root.get("deptnoId"), deptno));
				}
				return pcPredicate;
			}
		},pageable);
		
		return users;
	}

	@Override
	public Integer count(String deptnoId) {
		return (int) MatterDao.count(new Specification<Matter>() {

			@Override
			public Predicate toPredicate(Root<Matter> root, CriteriaQuery<?> query, CriteriaBuilder cr) {
				Predicate predicate=cr.conjunction();
				predicate.getExpressions().add(cr.equal(root.get("deptnoId"),deptnoId));
				return predicate;
			}
		});
	}

}
