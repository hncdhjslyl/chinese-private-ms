/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package client;

import constants.GameConstants;
import handling.Buffstat;
import java.io.Serializable;

public enum MapleBuffStat implements Serializable, Buffstat {
//开始注释
    WATK(0x1, 1),//物理攻击
    WDEF(0x2, 1),//防御
    MATK(0x4, 1),//魔法攻击
    MDEF(0x8, 1),//防御
    ACC(0x10, 1),//命中
    AVOID(0x20, 1),//回避
    HANDS(0x40, 1),//手技
    SPEED(0x80, 1), //移动速度
    JUMP(0x100, 1), //跳跃力
    MAGIC_GUARD(0x200, 1),//魔法盾
    DARKSIGHT(0x400, 1), //隐身
    BOOSTER(0x800, 1), // 攻击加速
    POWERGUARD(0x1000, 1),//伤害反击
    MAXHP(0x2000, 1),//
    MAXMP(0x4000, 1),//
    INVINCIBLE(0x8000, 1),//神之保护
    SOULARROW(0x10000, 1),//无形箭弩
    //2 - debuff
    //4 - debuff
    //8 - debuff

    //1 - debuff
    COMBO(0x200000, 1),//斗气集中
    SUMMON(0x200000, 1), //hack buffstat for summons ^.- (does/should not increase damage... hopefully <3)
    //这是一个被hack的值 召唤兽
    WK_CHARGE(0x400000, 1),//属性攻击
    DRAGONBLOOD(0x800000, 1),//龙之心
    HOLY_SYMBOL(0x1000000, 1),//神圣标记
    MESOUP(0x2000000, 1),//聚财术
    SHADOWPARTNER(0x4000000, 1), // 影分身
    PICKPOCKET(0x8000000, 1),//敛财术
    PUPPET(0x8000000, 1), // HACK - shares buffmask with pickpocket - odin special ^.-
    //替身术
    MESOGUARD(0x10000000, 1),//金钱护盾
    HP_LOSS_GUARD(0x20000000, 1),//失血保护
    //4 - debuff
    //8 - debuff

    //1 - debuff
    MORPH(0x2, 2),//变身术
    RECOVERY(0x4, 2),//恢复
    MAPLE_WARRIOR(0x8, 2),//冒险岛勇士
    STANCE(0x10, 2),//双刃荆棘
    STATUS_RESIST(0x10, 2),//??
    ELEMENT_RESIST(0x20, 2),//??国服可能未出现
    SHARP_EYES(0x20, 2),//火眼晶晶
    MANA_REFLECTION(0x40, 2),//魔法反击
    //8 - debuff

    SPIRIT_CLAW(0x100, 2), // 暗器伤人
    INFINITY(0x200, 2),//终极无限
    HOLY_SHIELD(0x400, 2), //advanced blessing after ascension
    //进阶祝福
    HAMSTRING(0x800, 2),//幻影步
    BLIND(0x1000, 2),//致盲
    CONCENTRATE(0x2000, 2),//集中精神
    //4 - debuff
    ECHO_OF_HERO(0x8000, 2),//英雄回声
    MESO_RATE(0x10000, 2), //confirmed //金钱掉率
    GHOST_MORPH(0x20000, 2),//
    ARIANT_COSS_IMU(0x40000, 2), // The white ball around you
    //8 - debuff

    DROP_RATE(0x100000, 2), //d
    //2 = unknown
    EXPRATE(0x400000, 2),//经验率
    ACASH_RATE(0x800000, 2),
    ILLUSION(0x1000000, 2), //hack buffstat
    //2 和 4 ???定义 这里有偏移
    //2 and 4 are unknown
    BERSERK_FURY(0x8000000, 2),//狂暴战魂
    DIVINE_BODY(0x10000000, 2),//金刚霸体
    SPARK(0x20000000, 2),//闪光击
    ARIANT_COSS_IMU2(0x40000000, 2), // no idea, seems the same
    DEFENCE_R(0x4000000, 2),
    FINALATTACK(0x80000000, 2),//终极弓箭???
    //4 = unknown
    //偏移
    //0x1?
    ELEMENT_RESET(0x2, 3),//自然力重置
    WIND_WALK(0x4, 3),//风影漫步
    //0x8

    ARAN_COMBO(0x10, 3),//矛连击强化
    COMBO_DRAIN(0x20, 3),//连环吸血
    COMBO_BARRIER(0x40, 3),//战神之盾
    BODY_PRESSURE(0x80, 3),//战神抗压
    SMART_KNOCKBACK(0x100, 3),// 战神威势
    PYRAMID_PQ(0x200, 3),
    // 4 ?
    //8 - debuff

    //1 - debuff
    //2 - debuff
    SLOW(0x4000, 3), //缓速术
    MAGIC_SHIELD(0x8000, 3),//魔法屏障
    MAGIC_RESISTANCE(0x10000, 3),//抗魔领域
    SOUL_STONE(0x20000, 3),//灵魂之石
    SOARING(0x40000, 3),//飞行骑乘
    //8 - debuff

    LIGHTNING_CHARGE(0x100000, 3),//雷鸣冲击
    ENRAGE(0x200000, 3),//葵花宝典
    OWL_SPIRIT(0x400000, 3),//死亡猫头鹰
    //0x800000 debuff, shiny yellow

    FINAL_CUT(0x1000000, 3),//终极斩
    DAMAGE_BUFF(0x2000000, 3),
    ATTACK_BUFF(0x4000000, 3), //attack %? feline berserk
    RAINING_MINES(0x8000000, 3),//地雷
    //古老意志？？？
    ENHANCED_MAXHP(0x10000000, 3),
    ENHANCED_MAXMP(0x20000000, 3),
    ENHANCED_WATK(0x40000000, 3),
    ENHANCED_MATK(0x80000000, 3),
    MIN_CRITICAL_DAMAGE(0x80000000, 3),
    //偏移

    ENHANCED_WDEF(0x1, 4),
    ENHANCED_MDEF(0x2, 4),
    PERFECT_ARMOR(0x4, 4),//完美机甲
    SATELLITESAFE_PROC(0x8, 4),
    SATELLITESAFE_ABSORB(0x10, 4),
    TORNADO(0x20, 4),
    CRITICAL_RATE_BUFF(0x40, 4),
    MP_BUFF(0x80, 4),
    DAMAGE_TAKEN_BUFF(0x100, 4),
    DODGE_CHANGE_BUFF(0x200, 4),
    CONVERSION(0x400, 4),
    REAPER(0x800, 4),
    INFILTRATE(0x1000, 4),
    MECH_CHANGE(0x2000, 4),
    AURA(0x4000, 4),
    DARK_AURA(0x8000, 4),
    BLUE_AURA(0x10000, 4),
    YELLOW_AURA(0x20000, 4),
    BODY_BOOST(0x40000, 4),
    FELINE_BERSERK(0x80000, 4),
    DICE_ROLL(0x100000, 4),
    DIVINE_SHIELD(0x200000, 4),
    PIRATES_REVENGE(0x400000, 4),
    TELEPORT_MASTERY(0x800000, 4),
    COMBAT_ORDERS(0x1000000, 4),
    BEHOLDER(0x2000000, 4),
    //4 = debuff
    GIANT_POTION(0x8000000, 4),
    ONYX_SHROUD(0x10000000, 4),
    ONYX_WILL(0x20000000, 4),
    //4 = debuff
    BLESS(0x80000000, 4),
    //1 //blue star + debuff
    //2 debuff	 but idk
    THREATEN_PVP(0x4, 5),
    ICE_KNIGHT(0x8, 5),
    //1 debuff idk.
    //2 unknown
    STR(0x40, 5),
    INT(0x80, 5),
    DEX(0x100, 5),
    LUK(0x200, 5),
    //4 unknown
    //8 unknown tornado debuff? - hp

    ANGEL_ATK(0x1000, 5, true),
    ANGEL_MATK(0x2000, 5, true),
    HP_BOOST(0x4000, 5, true), //indie hp
    MP_BOOST(0x8000, 5, true),
    ANGEL_ACC(0x10000, 5, true),
    ANGEL_AVOID(0x20000, 5, true),
    ANGEL_JUMP(0x40000, 5, true),
    ANGEL_SPEED(0x80000, 5, true),
    ANGEL_STAT(0x100000, 5, true),
    PVP_DAMAGE(0x200000, 5),
    PVP_ATTACK(0x400000, 5), //skills
    INVINCIBILITY(0x800000, 5),
    HIDDEN_POTENTIAL(0x1000000, 5),
    ELEMENT_WEAKEN(0x2000000, 5),
    SNATCH(0x4000000, 5), //however skillid is 90002000, 1500 duration
    FROZEN(0x8000000, 5),
    //1, unknown
    ICE_SKILL(0x20000000, 5),
    //4 - debuff
    BOUNDLESS_RAGE(0x80000000, 5),
    // 1 unknown
    //2 = debuff
    //4 unknown
    //8 unknown

    HOLY_MAGIC_SHELL(0x10, 6), //max amount of attacks absorbed
    //2 unknown a debuff
    ARCANE_AIM(0x40, 6, true),
    BUFF_MASTERY(0x80, 6), //buff duration increase

    ABNORMAL_STATUS_R(0x100, 6), // %
    ELEMENTAL_STATUS_R(0x200, 6), // %
    WATER_SHIELD(0x400, 6),
    DARK_METAMORPHOSIS(0x800, 6), // mob count
    BARREL_ROLL(GameConstants.GMS ? 0x1000 : 0x100, 6),
    SPIRIT_SURGE(0x2000, 6),
    SPIRIT_LINK(0x4000, 6, true),
    //8 unknown

    VIRTUE_EFFECT(0x10000, 6),
    //2, 4, 8 unknown

    NO_SLIP(0x100000, 6),
    FAMILIAR_SHADOW(0x200000, 6),
    // 4
    // 8

    //CRITICAL_RATE(0x1000000, 6),
    //0x2000000 unknown
    //0x4000000 unknown DEBUFF?
    //0x8000000 unknown DEBUFF?

    // 1 unknown	
    // 2 unknown	
    DEFENCE_BOOST_R(0x40000000, 6), // weapon def and magic def
    // 8 unknown

    // 0x1
    // 0x2
    // 0x4
    // 0x8 got somekind of effect when buff ends...

    // 0x10
    UNKNOWN8(0x20, 7),
    // 0x40 add attack, 425 wd, 425 md, 260 for acc, and avoid
    // 0x80

    // 0x100	
    HP_BOOST_PERCENT(0x200, 7, true),
    MP_BOOST_PERCENT(0x400, 7, true),
    //WEAPON_ATTACK(0x800, 7),

    UNKNOWN12(0x1000, 7), //+ 5003 wd
    // 0x2000,
    // 0x4000, true
    // 0x8000

    // WEAPON ATTACK 0x10000, true
    // 0x20000, true
    // 0x40000, true
    // 0x80000, true

    // 0x100000  true
    // 0x200000 idk
    // 0x400000  true
    UNKNOWN9(0x800000, 7),

    UNKNOWN10(0x10, 8),
    KILL_COUNT(GameConstants.GMS ? 0x800000 : 0x80000, 7),
    PHANTOM_MOVE(GameConstants.GMS ? 0x8 : 0x80000000, GameConstants.GMS ? 8 : 7),
    JUDGMENT_DRAW(GameConstants.GMS ? 0x10 : 0x1, 8),
      ABSORB_DAMAGE_HP(536870912, 6), 
    ENERGY_CHARGE(0x2000000, 8),
    DASH_SPEED(0x4000000, 8),
    DASH_JUMP(0x8000000, 8),
    MONSTER_RIDING(0x10000000, 8),
    SPEED_INFUSION(0x20000000, 8),
    HOMING_BEACON(0x40000000, 8),
    ARIA_ARMOR(0x400000, 7), //LOL ARE U STUPID
    DEFAULT_BUFFSTAT(0x80000000, 8);
    private static final long serialVersionUID = 0L;
    private final int buffstat;
    private final int first;
    private boolean stacked = false;
    // [8] [7] [6] [5] [4] [3] [2] [1]
    // [0] [1] [2] [3] [4] [5] [6] [7]

    private MapleBuffStat(int buffstat, int first) {
        this.buffstat = buffstat;
        this.first = first;
    }

    private MapleBuffStat(int buffstat, int first, boolean stacked) {
        this.buffstat = buffstat;
        this.first = first;
        this.stacked = stacked;
    }

    public final int getPosition() {
        return getPosition(false);
    }

    public final int getPosition(boolean fromZero) {
        if (!fromZero) {
            return first; // normal one
        }
        switch (first) {
            case 8:
                return 0;
            case 7:
                return 1;
            case 6:
                return 2;
            case 5:
                return 3;
            case 4:
                return 4;
            case 3:
                return 5;
            case 2:
                return 6;
            case 1:
                return 7;
        }
        return 0; // none
    }

    public final int getValue() {
        return buffstat;
    }

    public final boolean canStack() {
        return stacked;
    }
}
