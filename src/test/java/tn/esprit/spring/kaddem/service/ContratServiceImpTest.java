package tn.esprit.spring.kaddem.service;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ContratServiceImpTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllContrats() {
        List<Contrat> contrats = new ArrayList<>();
        when(contratRepository.findAll()).thenReturn(contrats);

        List<Contrat> result = contratService.retrieveAllContrats();

        assertEquals(contrats, result);
    }



    @Test
    public void testUpdateContrat() {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        contrat.setMontantContrat(100);

        Integer nouveauMontant = 200;
        contrat.setMontantContrat(nouveauMontant);

        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat contratMiseAJour = contratService.updateContrat(contrat);

        assertEquals(nouveauMontant, contratMiseAJour.getMontantContrat());
    }


}
