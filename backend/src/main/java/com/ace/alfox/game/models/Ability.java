package com.ace.alfox.game.models;

import com.ace.alfox.game.TargetTypes;
import com.ace.alfox.lib.data.Database;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Ability {
    @Id public NitriteId id;
    public int damageRoll;
    public int damageModifier;
    public TargetTypes targetType;
    public int cooldownTime;

}


