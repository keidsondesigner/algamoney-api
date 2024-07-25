package com.keidson.algamoney_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keidson.algamoney_api.model.LancamentoModel;

public interface LacamentoRepository extends JpaRepository<LancamentoModel, Long> {

  @Query(""" 
    SELECT l FROM LancamentoModel l 
    WHERE (:descricao IS NULL OR l.descricao ILIKE %:descricao%)
    AND (COALESCE(:dataVencimentoDe, l.dataVencimento) = l.dataVencimento OR l.dataVencimento >= :dataVencimentoDe)
    AND (COALESCE(:dataVencimentoAte, l.dataVencimento) = l.dataVencimento OR l.dataVencimento <= :dataVencimentoAte)
  """)
  List<LancamentoModel> findByDescricaoAndDataVencimento(
    @Param("descricao") String descricao,
    @Param("dataVencimentoDe") LocalDate dataVencimentoDe,
    @Param("dataVencimentoAte") LocalDate dataVencimentoAte);

}
