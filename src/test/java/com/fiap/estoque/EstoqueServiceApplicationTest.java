package com.fiap.estoque;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceApplicationTest {

    @Test
    void deveVerificarSeClassePossuiAnotacaoSpringBootApplication() {
        boolean possuiAnotacao = EstoqueServiceApplication.class.isAnnotationPresent(SpringBootApplication.class);

        assertTrue(possuiAnotacao);
    }

    @Test
    void deveVerificarSeMetodoMainExiste() {
        assertDoesNotThrow(() -> {
            EstoqueServiceApplication.class.getDeclaredMethod("main", String[].class);
        });
    }

    @Test
    void deveExecutarMetodoMainSemExcecao() {
        String[] argumentos = {"--spring.main.web-application-type=none", "--spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"};

        try (MockedStatic<SpringApplication> mockSpringApplication = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
            mockSpringApplication.when(() -> SpringApplication.run(EstoqueServiceApplication.class, argumentos))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> EstoqueServiceApplication.main(argumentos));

            mockSpringApplication.verify(() -> SpringApplication.run(EstoqueServiceApplication.class, argumentos), times(1));
        }
    }

    @Test
    void deveExecutarMetodoMainComArgumentosVazios() {
        String[] argumentosVazios = {};

        try (MockedStatic<SpringApplication> mockSpringApplication = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
            mockSpringApplication.when(() -> SpringApplication.run(EstoqueServiceApplication.class, argumentosVazios))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> EstoqueServiceApplication.main(argumentosVazios));

            mockSpringApplication.verify(() -> SpringApplication.run(EstoqueServiceApplication.class, argumentosVazios), times(1));
        }
    }

    @Test
    void deveExecutarMetodoMainComArgumentosNulos() {
        String[] argumentosNulos = null;

        try (MockedStatic<SpringApplication> mockSpringApplication = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
            mockSpringApplication.when(() -> SpringApplication.run(EstoqueServiceApplication.class, argumentosNulos))
                    .thenReturn(mockContext);

            assertDoesNotThrow(() -> EstoqueServiceApplication.main(argumentosNulos));

            mockSpringApplication.verify(() -> SpringApplication.run(EstoqueServiceApplication.class, argumentosNulos), times(1));
        }
    }

    @Test
    void deveVerificarSeClasseEPublica() {
        int modificadores = EstoqueServiceApplication.class.getModifiers();

        assertTrue(java.lang.reflect.Modifier.isPublic(modificadores));
    }

    @Test
    void deveVerificarSeClasseNaoEAbstrata() {
        int modificadores = EstoqueServiceApplication.class.getModifiers();

        assertFalse(java.lang.reflect.Modifier.isAbstract(modificadores));
    }

    @Test
    void deveVerificarSeClasseNaoEFinal() {
        int modificadores = EstoqueServiceApplication.class.getModifiers();

        assertFalse(java.lang.reflect.Modifier.isFinal(modificadores));
    }

    @Test
    void deveVerificarSeMetodoMainEEstatico() throws NoSuchMethodException {
        var metodoMain = EstoqueServiceApplication.class.getDeclaredMethod("main", String[].class);
        int modificadores = metodoMain.getModifiers();

        assertTrue(java.lang.reflect.Modifier.isStatic(modificadores));
    }

    @Test
    void deveVerificarSeMetodoMainEPublico() throws NoSuchMethodException {
        var metodoMain = EstoqueServiceApplication.class.getDeclaredMethod("main", String[].class);
        int modificadores = metodoMain.getModifiers();

        assertTrue(java.lang.reflect.Modifier.isPublic(modificadores));
    }

    @Test
    void deveVerificarSeMetodoMainRetornaVoid() throws NoSuchMethodException {
        var metodoMain = EstoqueServiceApplication.class.getDeclaredMethod("main", String[].class);
        Class<?> tipoRetorno = metodoMain.getReturnType();

        assertEquals(void.class, tipoRetorno);
    }
}