//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.client;

import com.nobody174.petevolution.component.PetData;

public final class ClientPetState {
    private static PetData activePetData;

    private ClientPetState() {
    }

    public static void setActivePetData(PetData data) {
        activePetData = data;
    }

    public static PetData getActivePetData() {
        return activePetData;
    }
}

// Built with assistance from Claude Code by Anthropic.
