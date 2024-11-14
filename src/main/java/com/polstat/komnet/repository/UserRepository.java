package com.polstat.komnet.repository;

import com.polstat.komnet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findOptionalByEmail(String email);
    List<User> findByNamaContaining(String nama);
    List<User> findByNimContaining(String nim);
    User findByNim(String nim);
    List<User> findByKelas(String kelas);
    List<User> findByEmailContaining(String email);
    List<User> findByStatusPembayaranKas(String statusPembayaranKas);
}
