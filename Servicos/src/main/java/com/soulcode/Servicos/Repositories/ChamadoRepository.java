package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado,Integer> {

    List<Chamado> findByCliente(Optional<Cliente> cliente);

    List<Chamado> findByFuncionario(Optional<Funcionario> funcionario);

    @Query(value = "SELECT * FROM chamado WHERE status =:status",nativeQuery = true )
    List<Chamado> findByStatus(String status);

    @Query(value="SELECT * FROM chamado WHERE data_entrada BETWEEN :data1 AND :data2", nativeQuery = true)
    List<Chamado> findByIntervaloData(Date data1, Date data2);

    @Query(value ="SELECT * FROM chamado AS c RIGHT JOIN pagamento AS p on c.id_pagamento = p.id_pagamento where p.status = 'QUITADO';", nativeQuery = true)
    List<Chamado> findByStatusPagamentoQuitado();

    @Query(value ="SELECT * FROM chamado AS c RIGHT JOIN pagamento AS p on c.id_pagamento = p.id_pagamento where p.status = 'LANCADO';", nativeQuery = true)
    List<Chamado> findByStatusPagamentoLancado();

    @Query(value = "SELECT status, COUNT(*) FROM chamado GROUP BY status", nativeQuery = true)
    List<Object> findByChamadoPorStatus();
}
