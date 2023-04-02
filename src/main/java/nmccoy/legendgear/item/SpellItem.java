package nmccoy.legendgear.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import nmccoy.legendgear.CustomAttributes;
import nmccoy.legendgear.LegendGear2;
import nmccoy.legendgear.entity.EntitySpellEffect;
import nmccoy.legendgear.entity.EntitySpellEffect.SpellID;

import com.google.common.collect.Multimap;

public class SpellItem extends Item {

	protected  double baseCastRange = 7;
	protected  double baseCastRadius = 2f;
	protected  double baseCastTime = 2f;
	protected  double baseCritBonus = 0;
	protected  double baseArcanePower = 3;
	protected  int baseMeleeDamage = 2;
	
	protected  SpellID spellType;
	
	protected  boolean isStaff = false;
	public boolean hitsWater = false;
	
	public static final int CRIT_WINDOW = 5;
	
	public static final UUID weaponPowerUUID = UUID.fromString("1556de23-3415-43b9-8b3b-1706b408759b"); 
	public static final UUID weaponRangeUUID = UUID.fromString("3f067f1d-4f88-456c-a45c-973870817514");
	public static final UUID weaponRadiusUUID= UUID.fromString("9150e1fb-32ea-4ca1-9e83-fd2e7473e95d"); 
	public static final UUID weaponSurgeUUID=UUID.fromString("b0ce01be-bd12-4864-890f-50b20969b188");
	//public static final AttributeModifier weaponPower = new AttributeModifier(weaponStatsUUID, "Weapon Properties", 13, 0);
	
	@Override
	public boolean isDamageable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public int getMaxDamage() {
		// TODO Auto-generated method stub
		return super.getMaxDamage();
	}
	
	public SpellItem() {
		// TODO Auto-generated constructor stub
		setCreativeTab(LegendGear2.legendgearTab);
		setUnlocalizedName("genericSpellItem");
		setMaxStackSize(1);
	}
	
	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		// TODO Auto-generated method stub
		Multimap map = super.getAttributeModifiers(stack);
		map.put(CustomAttributes.arcanePower.getAttributeUnlocalizedName(), new AttributeModifier(weaponPowerUUID, "Weapon Power", getBasePower(stack), 0));
		map.put(CustomAttributes.spellRange.getAttributeUnlocalizedName(), new AttributeModifier(weaponRangeUUID, "Weapon Range", getBaseRange(stack), 0));
		map.put(CustomAttributes.spellRadius.getAttributeUnlocalizedName(), new AttributeModifier(weaponRadiusUUID, "Weapon Radius", getBaseRadius(stack), 0));
		map.put(CustomAttributes.spellSurgePower.getAttributeUnlocalizedName(), new AttributeModifier(weaponSurgeUUID, "Weapon Surge", getBaseCritBonus(stack), 0));
		map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)getSmackDamage(stack), 0));
		return map;
	}
	
	protected double getBaseCastTime(ItemStack stack)
	{
		return baseCastTime;
	}
	protected  double getBaseCritBonus(ItemStack stack)
	{
		return baseCritBonus;
	}
	protected  double getBasePower(ItemStack stack)
	{
		return baseArcanePower;
	}
	protected  double getBaseRange(ItemStack stack)
	{
		return baseCastRange;
	}
	protected  double getBaseRadius(ItemStack stack)
	{
		return baseCastRadius;
	}
	protected  int getSmackDamage(ItemStack stack)
	{
		return baseMeleeDamage;
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 65535;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		// TODO Auto-generated method stub
		return EnumAction.bow;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world,
			EntityPlayer player, int count) {
		// TODO Auto-generated method stub
		int ticks =getMaxItemUseDuration(stack) - count;
		int totalTicks =getCastingTicks(stack, player); 
		if(ticks >= totalTicks)
		{
			double radius = getPlayerCastRadius(player);
			double power = getPlayerPower(player);
			boolean crit = false;
			if(ticks - totalTicks <= CRIT_WINDOW)
			{
				power += getPlayerCritBonus(player);
				player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":spellcrit", 0.3f, 1.0f);
				crit = true;
				//if(player.worldObj.isRemote) 
			}
			Vec3 from = Vec3.createVectorHelper(player.posX, player.posY+player.eyeHeight, player.posZ);
			Vec3 castPoint = getRayTargetResult(from, player.getLookVec(), getPlayerCastRange(player), world, hitsWater);
			stack.damageItem(1, player);
			GenerateSpell(player, spellType, castPoint, getPlayerCastRadius(player), power, crit);
			player.addExhaustion(1);
		}
	}
	
	
	@Override public boolean hitEntity(ItemStack stack, net.minecraft.entity.EntityLivingBase target, net.minecraft.entity.EntityLivingBase wielder) {
		if(!wielder.worldObj.isRemote)
		{
			boolean flag = wielder.fallDistance > 0.0F && !wielder.onGround && !wielder.isOnLadder() && !wielder.isInWater() && !wielder.isPotionActive(Potion.blindness) && wielder.ridingEntity == null;
				if(flag && isStaff && wielder instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) wielder;
					stack.damageItem(1,  wielder);
					GenerateSpell(player, spellType, Vec3.createVectorHelper(target.posX, target.posY+target.height/2, target.posZ), getPlayerCastRadius(player)/2, getPlayerPower(player)/2, false);
				}
		}
		stack.damageItem(2, wielder);
		return true;
	};
	
	public void GenerateSpell(EntityPlayer caster, SpellID id, Vec3 location, double radius, double power, boolean critical)
	{
		if(!caster.worldObj.isRemote)
		{
			caster.worldObj.spawnEntityInWorld(new EntitySpellEffect(caster.worldObj, id, caster, location, radius, power, critical));
			//caster.worldObj.createExplosion(null, location.xCoord, location.yCoord, location.zCoord, (float)power, true);
			System.out.println(power);
		}
	}
	
	public static Vec3 getRayTargetResult(Vec3 from, Vec3 direction, double maxDistance, World world, boolean water)
	{
		Vec3 castDestination = from.addVector(direction.xCoord*maxDistance, direction.yCoord*maxDistance, direction.zCoord*maxDistance);
		Vec3 copyFrom = Vec3.createVectorHelper(from.xCoord, from.yCoord, from.zCoord);
		MovingObjectPosition mop = world.rayTraceBlocks(copyFrom, castDestination, water);
		if(mop != null)
		{
			Vec3 hitPos = mop.hitVec;
			if(hitPos.distanceTo(from) < maxDistance) return hitPos;
		}
		direction = direction.normalize();
		direction.xCoord *= maxDistance;
		direction.yCoord *= maxDistance;
		direction.zCoord *= maxDistance;
		return from.addVector(direction.xCoord, direction.yCoord, direction.zCoord);
	}
	
	public float getCastingProgress(ItemStack stack, EntityPlayer player, float subtick)
	{
		return Math.min((player.getItemInUseDuration()+subtick)/getCastingTicks(stack, player), 1);
	}
	
	
	public int getCastingTicks(ItemStack stack, EntityPlayer player)
	{
		double penalty = getArmorCastingPenalty(player);
		if(penalty >= 1) return Integer.MAX_VALUE;
		double time = getBaseCastTime(stack)/(1-penalty);
		return (int) (time*20);
	}

	
	public static double getPlayerPower(EntityPlayer player)
	{
		return player.getAttributeMap().getAttributeInstance(CustomAttributes.arcanePower).getAttributeValue();
	}
	
	public static double getPlayerCastRange(EntityPlayer player)
	{
		return player.getAttributeMap().getAttributeInstance(CustomAttributes.spellRange).getAttributeValue();
	}
	
	public static double getPlayerCastRadius(EntityPlayer player)
	{
		return  player.getAttributeMap().getAttributeInstance(CustomAttributes.spellRadius).getAttributeValue();
	}
	
	public static double getPlayerCritBonus(EntityPlayer player)
	{
		return  player.getAttributeMap().getAttributeInstance(CustomAttributes.spellSurgePower).getAttributeValue();
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		// TODO Auto-generated method stub
		int ticks = getMaxItemUseDuration(stack)-count;
		int cast_time = getCastingTicks(stack, player);
		
		if(!player.worldObj.isRemote)
		{
			if(ticks == cast_time/3)
				player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":highcharge", 0.3f, 0.5f);
			if(ticks == cast_time*2/3)
				player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":highcharge", 0.3f, 0.75f);
			if(ticks == cast_time)
				player.worldObj.playSoundAtEntity(player, LegendGear2.MODID+":highcharge", 0.3f, 1.0f);
		}
		
		//System.out.println(ticks);
		//PlayerStarstatsExtension.get(player).castingProgress = Math.min(ticks*1.0f/cast_time, 1);
	}
	
	@Override public ItemStack 
	onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	/*	if(!world.isRemote)
		{
			Vec3 from = Vec3.createVectorHelper(player.posX, player.posY+player.eyeHeight, player.posZ);
			Vec3 castPoint = getRayTargetResult(from, player.getLookVec(), getCastRange(stack), world);
			world.createExplosion(player, castPoint.xCoord, castPoint.yCoord, castPoint.zCoord, 1, true);
		}*/
		player.setItemInUse(stack, getMaxItemUseDuration(stack));
		return stack;
	};

	public static double getArmorCastingPenalty(EntityPlayer player)
	{
		double basePenalty = player.getTotalArmorValue()/25D;
		double scaling = player.getAttributeMap().getAttributeInstance(CustomAttributes.armorPenaltyScaling).getAttributeValue();
		double total = scaling * basePenalty;
		if(total < 0) total = 0;
		if(total > 1) total = 1;
		return total;
	}
	
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer player,
			List list, boolean p_77624_4_) {
		// TODO Auto-generated method stub
		super.addInformation(p_77624_1_, player, list, p_77624_4_);
		
		double armorPenalty = getArmorCastingPenalty(player);
		if(armorPenalty > 0)
		{
			String penaltyText = EnumChatFormatting.RED+I18n.format("lg2text.armorPenaltyAnnotation", Math.round(armorPenalty*100));
			list.add(penaltyText);
		}
	}
}
