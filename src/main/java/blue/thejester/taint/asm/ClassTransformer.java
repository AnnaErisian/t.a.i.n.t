package blue.thejester.taint.asm;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

import java.util.Iterator;

import blue.thejester.taint.asm.names.ObfuscatedName;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformer implements IClassTransformer
{
	public static Logger logger = LogManager.getLogger("TaintCore");

	final String asmHandler = "blue/thejester/taint/asm/handler/AsmHandler";

	public static int transformations = 0;

	public ClassTransformer()
	{
		logger.log(Level.DEBUG, "Starting Class Transformation");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{

		logger.log(Level.DEBUG, "Attempting Transform from Taint");
		if (transformedName.equals("net.minecraft.entity.EntityLivingBase"))
		{
			transformations++;
			return patchEntityLivingBase(basicClass);
		}

		return basicClass;
	}



	private byte[] patchEntityLivingBase(byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityLivingBase Class: " + classNode.name);

		MethodNode updatePotionEffects = null;
		MethodNode travel = null;

		ObfuscatedName travelFunc = new ObfuscatedName("func_191986_a");

		for (MethodNode mn : classNode.methods)
		{
			if (mn.name.equals(travelFunc.get()))
			{
				travel = mn;
			}
		}

		if (travel != null)
		{

			ObfuscatedName entityWorld = new ObfuscatedName("field_70170_p");
			ObfuscatedName getBlockState = new ObfuscatedName("func_180495_p");
			ObfuscatedName getBlock = new ObfuscatedName("func_177230_c");

			//For each instruction
			for (int i = 0; i < travel.instructions.size(); i++) {
				AbstractInsnNode ain = travel.instructions.get(i);
				//if it's pushing something to the stack
				if (ain instanceof LdcInsnNode)
				{
					LdcInsnNode lin = (LdcInsnNode) ain;
					//and that thing is 0.91f
					if (lin.cst.equals(new Float("0.91")))
					{
						AbstractInsnNode next = travel.instructions.get(i + 1);
						//and we're then multiplying floats - the operation after our insertion point will be the storing of the value
						if (next.getOpcode() == Opcodes.FMUL)
						{
							//We're in the right place -
							InsnList toInsert = new InsnList();
							//Load the entity twice onto the stack
							toInsert.add(new VarInsnNode(ALOAD, 0));
							toInsert.add(new VarInsnNode(ALOAD, 0));
							//Get World from the entity
							toInsert.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/EntityLivingBase", entityWorld.get(), "Lnet/minecraft/world/World;"));
							//Load the block position onto the stack
							toInsert.add(new VarInsnNode(ALOAD, 5));
							//Call getBlockState, leaving a BlockState on the stack
							toInsert.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/world/World", getBlockState.get(), "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false));
							//Call getBlock, leaving a Block on the stack
							toInsert.add(new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", getBlock.get(), "()Lnet/minecraft/block/Block;", true));
							//Call our code, I think leaving the return value to be stored
							toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "slipFix", "(FLnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/block/Block;)F", false));

							//jam the instructions in and skip over them - no need to read the things we just added
							travel.instructions.insert(next, toInsert);
							i += 6;
						}
					}
				}
			}
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}


	private byte[] patchDummyClass(byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found Dummy Class: " + classNode.name);

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

	public int getNextIndex(MethodNode mn)
	{
		Iterator it = mn.localVariables.iterator();
		int max = 0;
		int next = 0;
		while (it.hasNext())
		{
			LocalVariableNode var = (LocalVariableNode) it.next();
			int index = var.index;
			if (index >= max)
			{
				max = index;
				next = max + Type.getType(var.desc).getSize();
			}
		}
		return next;
	}
}
