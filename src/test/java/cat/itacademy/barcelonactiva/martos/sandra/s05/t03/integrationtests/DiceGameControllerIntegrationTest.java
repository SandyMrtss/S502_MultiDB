package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.integrationtests;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.Request.PlayerDTORequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DiceGameControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() throws IOException {
        MongoTestConfig.startMongo();
    }
    @DisplayName("Tests getWinner")
    @Test
    @Order(1)
    void whenGetWinner_thenReturnStatusOkAndWinner() throws Exception {
        mockMvc.perform(get("/diceGame/v1/players/ranking/winner"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("maya"))
                .andExpect(jsonPath("$.successRate").value(100.0));
    }

    @DisplayName("Tests getLoser")
    @Test
    @Order(2)
    void whenGetLoser_thenReturnStatusOkAndLoser() throws Exception {
        mockMvc.perform(get("/diceGame/v1/players/ranking/loser"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("sandy"))
                .andExpect(jsonPath("$.successRate").value(33.33));
    }

    @DisplayName("Tests getAllSuccessRate")
    @Test
    @Order(3)
    void whenGetAllSuccessRate_thenReturnStatusOkAndSuccessRateList() throws Exception {
        mockMvc.perform(get("/diceGame/v1/players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @DisplayName("Tests getAvgRanking")
    @Test
    @Order(4)
    void whenGetRanking_thenReturnStatusOkAndSuccessRateList() throws Exception {
        mockMvc.perform(get("/diceGame/v1/players/ranking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(Math.round(61.1)));
    }

    @DisplayName("Test newPlayer")
    @Test
    @Order(5)
    void whenNewPlayer_thenReturnStatusCreatedAndMessage() throws Exception {
        PlayerDTORequest newPlayer = new PlayerDTORequest("juan");
        mockMvc.perform(post("/diceGame/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPlayer)))
                //.with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Player added successfully"));
    }

    @DisplayName("Test newPlayer duplicated throws exception")
    @Test
    @Order(6)
    void whenNewPlayerExists_thenReturnStatusConflictAndException() throws Exception {
        PlayerDTORequest newPlayer = new PlayerDTORequest("javi");
        mockMvc.perform(post("/diceGame/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPlayer)))
                //.with(csrf()))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("Username already used"));
    }


    @DisplayName("Test updatePlayer")
    @Test
    @Order(7)
    void whenUpdatePlayer_thenReturnStatusOkAndMessage() throws Exception {
        PlayerDTORequest updatedPlayer = new PlayerDTORequest("javis");
        mockMvc.perform(put("/diceGame/v1/players/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPlayer)))
                //.with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Player updated successfully"));
    }

    @DisplayName("Test updatePlayer when username already exists")
    @Test
    @Order(8)
    void whenUpdatePlayerDuplicated_thenReturnStatusOkAndMessage() throws Exception {
        PlayerDTORequest updatedPlayer = new PlayerDTORequest("sandy");
        mockMvc.perform(put("/diceGame/v1/players/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("sandy")))
                //.with(csrf()))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("Username already used"));
    }

    @DisplayName("Test play new game")
    @Test
    @Order(9)
    void whenPlayGame_thenReturnStatusOkAndGame() throws Exception {
        mockMvc.perform(post("/diceGame/v1/players/{id}/games", 3)
                        .contentType(MediaType.APPLICATION_JSON))
                //.with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dice1").isNumber())
                .andExpect(jsonPath("$.dice2").isNumber())
                .andExpect(jsonPath("$.win").isBoolean());

    }

    @DisplayName("Test play game user doesn't exists throws exception")
    @Test
    @Order(10)
    void whenPlayGameUserNotFound_thenReturnStatusNotFound() throws Exception {
        mockMvc.perform(post("/diceGame/v1/players/{id}/games", 45)
                        .contentType(MediaType.APPLICATION_JSON))
                //.with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("No player was found with the chosen id"));
    }

    @DisplayName("Tests getAllGames")
    @Test
    @Order(11)
    void whenGetAllGames_thenReturnStatusOkAndList() throws Exception {
        mockMvc.perform(get("/diceGame/v1/players/{id}/games", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @DisplayName("Tests deleteAllGames")
    @Test
    @Order(12)
    void whenDeleteAllGames_thenReturnStatusOkAndMessage() throws Exception {
        mockMvc.perform(delete("/diceGame/v1/players/{id}/games", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("All games deleted successfully"));
    }

    @DisplayName("Tests getAllGames empty")
    @Test
    @Order(13)
    void whenGetAllGamesEmpty_thenReturnStatusOkAndEmptyList() throws Exception {
        mockMvc.perform(get("/diceGame/v1/players/{id}/games", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
