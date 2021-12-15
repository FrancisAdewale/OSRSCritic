package com.example.osrscritic.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osrscritic.databinding.SkillRowItemBinding
import com.example.osrscritic.model.Skillvalue

class StatsAdapter : RecyclerView.Adapter<StatsViewHolder>() {

    private val statsMutableList : MutableList<Skillvalue> = mutableListOf()

    fun setStatsList(statsList: List<Skillvalue>) {
        statsMutableList.addAll(statsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = SkillRowItemBinding.inflate(inflater)

        return StatsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {

        val stat = statsMutableList[position]

        when(stat.id){
            0 -> holder.binding.tvSkill.text = String.format("Skill: %s", "Attack")
            1 -> holder.binding.tvSkill.text = String.format("Skill: %s","Defence")
            2 -> holder.binding.tvSkill.text = String.format("Skill: %s","Strength")
            3 -> holder.binding.tvSkill.text = String.format("Skill: %s","Constitution")
            4 -> holder.binding.tvSkill.text = String.format("Skill: %s","Ranged")
            5 -> holder.binding.tvSkill.text = String.format("Skill: %s","Prayer")
            6 -> holder.binding.tvSkill.text = String.format("Skill: %s","Magic")
            7 -> holder.binding.tvSkill.text = String.format("Skill: %s","Cooking")
            8 -> holder.binding.tvSkill.text = String.format("Skill: %s","Woodcutting")
            9 -> holder.binding.tvSkill.text = String.format("Skill: %s","Fletching")
            10 -> holder.binding.tvSkill.text = String.format("Skill: %s","Firemaking")
            11 -> holder.binding.tvSkill.text = String.format("Skill: %s","Fishing")
            12 -> holder.binding.tvSkill.text = String.format("Skill: %s","Crating")
            13 -> holder.binding.tvSkill.text = String.format("Skill: %s","Smithing")
            14 -> holder.binding.tvSkill.text = String.format("Skill: %s","Mining")
            15 -> holder.binding.tvSkill.text = String.format("Skill: %s","Herblore")
            16 -> holder.binding.tvSkill.text = String.format("Skill: %s","Agility")
            17 -> holder.binding.tvSkill.text = String.format("Skill: %s","Thieving")
            18 -> holder.binding.tvSkill.text = String.format("Skill: %s","Slayer")
            19 -> holder.binding.tvSkill.text = String.format("Skill: %s","Farming")
            20 -> holder.binding.tvSkill.text = String.format("Skill: %s","Runecrafting")
            21 -> holder.binding.tvSkill.text = String.format("Skill: %s","Hunter")
            22 -> holder.binding.tvSkill.text = String.format("Skill: %s","Construction")
            23 -> holder.binding.tvSkill.text = String.format("Skill: %s","Summoning")
            24 -> holder.binding.tvSkill.text = String.format("Skill: %s","Dungoneering")
            25 -> holder.binding.tvSkill.text = String.format("Skill: %s","Divination")
            26 -> holder.binding.tvSkill.text = String.format("Skill: %s","Invention")
            else -> holder.binding.tvSkill.text = String.format("Skill: %s","Archaeology")
        }

        holder.binding.tvLevel.text = String.format("Level: %s", stat.level.toString())
        holder.binding.tvRank.text = String.format("Rank: %s",stat.rank.toString())
        holder.binding.tvXp.text = String.format("XP: %s",stat.xp.toString())
    }

    override fun getItemCount() = statsMutableList.size
}

class StatsViewHolder(val binding: SkillRowItemBinding) : RecyclerView.ViewHolder(binding.root)