package com.pri.petcationbackend.service;

import com.pri.petcationbackend.dao.PetOwnerRepository;
import com.pri.petcationbackend.dao.PetRepository;
import com.pri.petcationbackend.dao.PetTypeRepository;
import com.pri.petcationbackend.model.Pet;
import com.pri.petcationbackend.model.PetType;
import com.pri.petcationbackend.model.User;
import com.pri.petcationbackend.web.dto.PetDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService{

    private final PetRepository petRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final PetTypeRepository petTypeRepository;

    @Override
    public List<PetDto> getAllPetsByUser(User user) {
        if (user == null) {
            return null;
        }

        return petRepository.findAllByUser(user)
                .stream().map(Pet::toDto)
                .toList();
    }

    @Override
    public List<PetDto> getAllPets() {
        return petRepository.findAll().stream()
                .map(Pet::toDto)
                .toList();
    }

    @Override
    public PetDto addModifyPet(PetDto petDto, User currentUser) {
        if(currentUser != null) {
            petOwnerRepository.findByUser(currentUser)
                    .ifPresent(petOwner -> {
                        PetType petType = petTypeRepository.findByName(petDto.getPetType().name()).orElse(null);
                        if(petType != null)
                        {
                            petRepository.save(new Pet(petOwner, petType, petDto));
                        } else {
                            petRepository.save(new Pet(petOwner, petTypeRepository.save(new PetType(petDto.getPetType().name())), petDto));
                        }
                    });

        }
        return null;
    }

    @Override
    public void delete(Long id) {
        petRepository.findById(id).ifPresent(p -> petRepository.deleteById(id));
    }
}
