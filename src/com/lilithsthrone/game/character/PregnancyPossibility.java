package com.lilithsthrone.game.character;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.npc.misc.GenericFemaleNPC;
import com.lilithsthrone.game.character.npc.misc.GenericMaleNPC;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.1.66
 * @version 0.3
 * @author Innoxia
 */
public class PregnancyPossibility implements XMLSaving {
	
	private String motherId, fatherId;
	private float probability;
	
	public PregnancyPossibility(String motherId, String fatherId, float probability) {
		this.motherId = motherId;
		this.fatherId = fatherId;
		this.probability = probability;
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof PregnancyPossibility)
				&& ((PregnancyPossibility)o).getMotherId().equals(motherId)
				&& ((PregnancyPossibility)o).getFatherId().equals(fatherId)
				&& ((PregnancyPossibility)o).getProbability() == probability;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + motherId.hashCode();
		result = 31 * result + fatherId.hashCode();
		result = 31 * result + (int)probability;
		return result;
	}
	
	public Element saveAsXML(Element parentElement, Document doc) {
		Element element = doc.createElement("pregnancyPossibility");
		parentElement.appendChild(element);
		
		CharacterUtils.addAttribute(doc, element, "motherId", this.getMotherId());
		CharacterUtils.addAttribute(doc, element, "fatherId", this.getFatherId());
		CharacterUtils.addAttribute(doc, element, "probability", String.valueOf(this.getProbability()));
		
		return element;
	}
	
	public static PregnancyPossibility loadFromXML(Element parentElement, Document doc) {
		return new PregnancyPossibility(
				parentElement.getAttribute("motherId"),
				parentElement.getAttribute("fatherId"),
				Float.valueOf(parentElement.getAttribute("probability")));
	}
	
	public String getMotherId() {
		return motherId;
	}

	public String getFatherId() {
		return fatherId;
	}

	public GameCharacter getMother() {
		try {
			return Main.game.getNPCById(motherId);
		} catch (Exception e) {
			Util.logGetNpcByIdError("PregnancyPossibility.getMother()", motherId);
			return Main.game.getNpc(GenericFemaleNPC.class);
		}
	}

	public GameCharacter getFather() {
		try {
			return Main.game.getNPCById(fatherId);
		} catch (Exception e) {
			Util.logGetNpcByIdError("PregnancyPossibility.getFather()", fatherId);
			return Main.game.getNpc(GenericMaleNPC.class);
		}
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}
	
}
