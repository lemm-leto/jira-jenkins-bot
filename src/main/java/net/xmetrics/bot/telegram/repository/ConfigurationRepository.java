package net.xmetrics.bot.telegram.repository;

import net.xmetrics.bot.telegram.domain.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
