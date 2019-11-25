/**
 * @author Chittaranjan Sardar
 */
package com.crsardar.handson.java.springboot.data.repo;

import com.crsardar.handson.java.springboot.data.dao.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long>
{

    Page<Person> findByLastName(final String lastName, Pageable pageable);

    Page<Person> findByLastName(final String lastName, Sort sort);
}
