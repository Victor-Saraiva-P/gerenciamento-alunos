package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    // #1 teste para salvar sem matricula
    @Test
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula(null); // Definindo matrícula como null

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    // #2 teste para salvar um nome muito longo e dar erro
    @Test
    public void salvarNomeGrande() {
        Aluno aluno = new Aluno();
        aluno.setNome("Nome muito grande que talvez alguem tenha");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.BIOMEDICINA); // colocando um curso que não existe
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    // #3 teste para salvar um nome curto e dar erro
    @Test
    public void salvarNomeCurto() {
        Aluno aluno = new Aluno();
        aluno.setNome("Nome");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.BIOMEDICINA); // colocando um curso que não existe
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    // #4 teste para salvar uma matricula muito curta e dar erro
    @Test
    public void salvarMatriculaCurta() {
        Aluno aluno = new Aluno();
        aluno.setNome("Fulano");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.BIOMEDICINA); // colocando um curso que não existe
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("12");

        Assert.assertThrows(ConstraintViolationException.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }
}