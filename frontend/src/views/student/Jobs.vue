<template>
  <div class="jobs-container">
    <div class="search-bar">
      <el-input v-model="searchForm.keyword" placeholder="请输入岗位关键词" prefix-icon="el-icon-search">
        <template #append>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </template>
      </el-input>
    </div>
    
    <div class="filter-bar">
      <el-form :inline="true" :model="searchForm" class="filter-form">
        <el-form-item label="行业">
          <el-select v-model="searchForm.industry" placeholder="请选择行业">
            <el-option label="互联网" value="互联网"></el-option>
            <el-option label="金融" value="金融"></el-option>
            <el-option label="教育" value="教育"></el-option>
            <el-option label="医疗" value="医疗"></el-option>
            <el-option label="制造业" value="制造业"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地点">
          <el-select v-model="searchForm.location" placeholder="请选择地点">
            <el-option label="北京" value="北京"></el-option>
            <el-option label="上海" value="上海"></el-option>
            <el-option label="广州" value="广州"></el-option>
            <el-option label="深圳" value="深圳"></el-option>
            <el-option label="杭州" value="杭州"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="searchForm.education" placeholder="请选择学历">
            <el-option label="本科" value="本科"></el-option>
            <el-option label="硕士" value="硕士"></el-option>
            <el-option label="博士" value="博士"></el-option>
            <el-option label="大专" value="大专"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="经验">
          <el-select v-model="searchForm.experience" placeholder="请选择经验">
            <el-option label="应届毕业生" value="应届毕业生"></el-option>
            <el-option label="1-3年" value="1-3年"></el-option>
            <el-option label="3-5年" value="3-5年"></el-option>
            <el-option label="5年以上" value="5年以上"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetFilter">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="job-list">
      <el-card v-for="job in jobs" :key="job.id" class="job-card">
        <div class="job-header">
          <h3 class="job-title">{{ job.jobTitle }}</h3>
          <span class="job-salary">{{ job.salaryMin }}-{{ job.salaryMax }}元/月</span>
        </div>
        <div class="job-info">
          <span class="job-tag">{{ job.location }}</span>
          <span class="job-tag">{{ job.education }}</span>
          <span class="job-tag">{{ job.experience }}</span>
        </div>
        <div class="job-company">
          <span class="company-name">{{ job.companyName }}</span>
          <span class="industry">{{ job.industry }}</span>
        </div>
        <div class="job-description">{{ job.description.substring(0, 100) }}...</div>
        <div class="job-actions">
          <el-button type="primary" size="small" @click="viewJobDetail(job.id)">查看详情</el-button>
          <el-button size="small" @click="collectJob(job.id)">收藏</el-button>
        </div>
      </el-card>
    </div>
    
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { jobApi } from '../../api'
import { useJobStore } from '../../store'
import { ElMessage } from 'element-plus'

export default {
  name: 'StudentJobs',
  setup() {
    const router = useRouter()
    const jobStore = useJobStore()
    const jobs = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    
    const searchForm = reactive({
      keyword: '',
      industry: '',
      location: '',
      education: '',
      experience: ''
    })
    
    const loadJobs = async () => {
      try {
        const response = await jobApi.getJobs({
          page: currentPage.value,
          size: pageSize.value,
          industry: searchForm.industry,
          location: searchForm.location,
          education: searchForm.education,
          experience: searchForm.experience
        })
        if (response.success) {
          jobs.value = response.data
          total.value = response.total || jobs.value.length
        }
      } catch (error) {
        ElMessage.error('加载岗位失败')
      }
    }
    
    const handleSearch = async () => {
      currentPage.value = 1
      try {
        const response = await jobApi.searchJobs({
          keyword: searchForm.keyword,
          industry: searchForm.industry,
          location: searchForm.location,
          education: searchForm.education,
          experience: searchForm.experience
        })
        if (response.success) {
          jobs.value = response.data
          total.value = jobs.value.length
        }
      } catch (error) {
        ElMessage.error('搜索失败')
      }
    }
    
    const resetFilter = () => {
      searchForm.keyword = ''
      searchForm.industry = ''
      searchForm.location = ''
      searchForm.education = ''
      searchForm.experience = ''
      loadJobs()
    }
    
    const viewJobDetail = (jobId) => {
      router.push(`/student/job/${jobId}`)
    }
    
    const collectJob = (jobId) => {
      jobStore.addFavorite(jobId)
      ElMessage.success('收藏成功')
    }
    
    const handleSizeChange = (size) => {
      pageSize.value = size
      loadJobs()
    }
    
    const handleCurrentChange = (page) => {
      currentPage.value = page
      loadJobs()
    }
    
    onMounted(() => {
      loadJobs()
    })
    
    return {
      jobs,
      currentPage,
      pageSize,
      total,
      searchForm,
      handleSearch,
      resetFilter,
      viewJobDetail,
      collectJob,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.jobs-container {
  padding: 20px;
}

.search-bar {
  margin-bottom: 20px;
}

.filter-bar {
  margin-bottom: 20px;
  background-color: #f5f5f5;
  padding: 15px;
  border-radius: 8px;
}

.filter-form {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.job-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.job-card {
  transition: all 0.3s;
}

.job-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.job-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.job-title {
  font-size: 18px;
  font-weight: bold;
  color: #1890ff;
  margin: 0;
}

.job-salary {
  font-size: 16px;
  font-weight: bold;
  color: #f5222d;
}

.job-info {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

.job-tag {
  font-size: 12px;
  padding: 2px 8px;
  background-color: #e6f7ff;
  color: #1890ff;
  border-radius: 4px;
}

.job-company {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 14px;
}

.company-name {
  font-weight: 500;
}

.industry {
  color: #8c8c8c;
}

.job-description {
  margin-bottom: 15px;
  color: #595959;
  line-height: 1.5;
}

.job-actions {
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>