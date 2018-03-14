//package com.inmaytide.orbit.sys.dao.specification;
//
//import com.inmaytide.orbit.commons.query.Conditions;
//import com.inmaytide.orbit.sys.dao.link.UserOrganizationRepository;
//import com.inmaytide.orbit.sys.domain.User;
//import com.inmaytide.orbit.sys.domain.link.UserOrganization;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.lang.Nullable;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
///**
// * @author Moss
// * @since November 27, 2017
// */
//public class UserSpecification implements Specification<User> {
//
//    private static final long serialVersionUID = -3080100982254007594L;
//
//    private Conditions conditions;
//
//    private UserOrganizationRepository userOrganizationRepository;
//
//    public UserSpecification(Conditions conditions, UserOrganizationRepository userOrganizationRepository) {
//        this.conditions = conditions;
//        this.userOrganizationRepository = userOrganizationRepository;
//    }
//
//    @Nullable
//    @Override
//    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        List<Predicate> predicates = new ArrayList<>();
//        if (StringUtils.isNotBlank(conditions.getString("keywords"))) {
//            predicates.add(criteriaBuilder.like(root.get("username"), conditions.getString("keywords")));
//            predicates.add(criteriaBuilder.like(root.get("name"), conditions.getString("keywords")));
//        }
//        if (StringUtils.isNotBlank(conditions.getString("status"))) {
//            predicates.add(criteriaBuilder.equal(root.get("status"), conditions.getString("status")));
//        }
//        if (StringUtils.isNotBlank(conditions.getString("orgs"))) {
//            List<Long> orgs = Stream.of(StringUtils.split(conditions.getString("orgs"), ","))
//                    .map(NumberUtils::toLong)
//                    .collect(Collectors.toList());
//            List<Long> uids = userOrganizationRepository.streamByOIdIn(orgs)
//                    .map(UserOrganization::getuId).collect(Collectors.toList());
//            predicates.add(criteriaBuilder.in(root.get("id").in(uids)));
//        }
//        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//    }
//}
